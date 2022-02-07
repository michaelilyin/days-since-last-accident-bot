package net.dslab.slack.firebase

import com.google.cloud.firestore.Firestore
import io.quarkus.test.Mock
import org.mockito.kotlin.mock
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class FirestoreMockFactory {
    @Mock
    @Produces
    @ApplicationScoped
    fun firestoreMock(): Firestore {
        return mock()
    }
}
