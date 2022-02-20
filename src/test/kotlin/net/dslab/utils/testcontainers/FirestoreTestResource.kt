package net.dslab.utils.testcontainers

import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.NoCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.NoCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import io.quarkus.test.Mock
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.mockito.kotlin.mock
import org.testcontainers.containers.FirestoreEmulatorContainer
import org.testcontainers.utility.DockerImageName
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.enterprise.inject.Produces
import javax.inject.Singleton


class FirestoreTestResource : QuarkusTestResourceLifecycleManager {
    private lateinit var firestore: Firestore

    companion object {
        private val emulator = FirestoreEmulatorContainer(
            DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:367.0.0-emulators")
        )
    }

    @Mock
    @ApplicationScoped
    class GcpProviderFactoryMock {
        @Produces
        @Singleton
        @Default
        fun googleCredential(): GoogleCredentials {
            return mock()
        }

        @Produces
        @Singleton
        @Default
        fun credentialsProvider(): CredentialsProvider {
            return NoCredentialsProvider.create()
        }
    }

    override fun start(): MutableMap<String, String> {
        emulator.start()

        val projectName = "test-project"
        val options = FirestoreOptions.getDefaultInstance().toBuilder()
            .setHost(emulator.emulatorEndpoint)
            .setCredentials(NoCredentials.getInstance())
            .setProjectId(projectName)
            .build()
        firestore = options.service

        initData()

        return mutableMapOf(
            "quarkus.google.cloud.firestore.host-override" to emulator.emulatorEndpoint,
            "quarkus.google.cloud.project-id" to projectName
        )
    }

    override fun stop() {
        firestore.shutdown()
        emulator.stop()
    }

    private fun initData() {

    }
}
