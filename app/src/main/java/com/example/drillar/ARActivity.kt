package com.example.drillar

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drillar.databinding.ActivityAractivityBinding
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class ARActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAractivityBinding
    private lateinit var arFragment: ArFragment
    private var selectedModel: String = "cone.glb"
    private var currentAnchorNode: AnchorNode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        Toast.makeText(this, "Tap the screen to place the model", Toast.LENGTH_LONG).show()

        if (selectedModel == null) {
            Toast.makeText(this, "Select the 3D Model", Toast.LENGTH_SHORT).show()

        }

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchor = hitResult.createAnchor()
            placeObject(anchor)
        }

        binding.coneImage.setOnClickListener {
            selectedModel = "cone.glb"
            Toast.makeText(this, "3D Model selected", Toast.LENGTH_SHORT).show()
        }

        binding.cubeImage.setOnClickListener {
            selectedModel = "cube.glb"
            Toast.makeText(this, "3D Model selected", Toast.LENGTH_SHORT).show()
        }


    }

    private fun placeObject(anchor: Anchor) {

        currentAnchorNode?.let {
            arFragment.arSceneView.scene.removeChild(it)
            it.anchor?.detach()
        }

        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        currentAnchorNode = anchorNode

        val modelUri = Uri.parse(selectedModel)

        val renderableSource = RenderableSource.builder()
            .setSource(this, modelUri, RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .setScale(0.5f)
            .build()

        ModelRenderable.builder()
            .setSource(this, renderableSource)
            .setRegistryId(selectedModel)
            .build()
            .thenAccept { modelRenderable ->
                val node = com.google.ar.sceneform.Node()
                node.setParent(anchorNode)
                node.renderable = modelRenderable
                node.localScale = Vector3(0.1f, 0.1f, 0.1f)
            }
            .exceptionally { throwable ->
                Toast.makeText(this, "Model load error: ${throwable.message}", Toast.LENGTH_LONG).show()
                Log.e("ARActivity", "Model load failed", throwable)
                null
            }
    }
}
