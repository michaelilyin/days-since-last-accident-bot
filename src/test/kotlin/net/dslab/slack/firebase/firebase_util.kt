package net.dslab.slack.firebase

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.Query
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito
import org.mockito.kotlin.KStubbing
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

internal class MockBuilder(
    private val path: List<String>,
    private val linkCollection: (String, CollectionReference) -> Any
) {
    internal fun docSnapshot(
        configure: KStubbing<DocumentSnapshot>.(DocumentSnapshot) -> Unit
    ): DocumentSnapshot {
        if (path.size.mod(2) == 1) {
            fail("Incorrect params")
        }

        val documentMock = mock<DocumentSnapshot>()
        val futureMock = mock<ApiFuture<DocumentSnapshot>> {
            on { get() } .thenReturn(documentMock)
        }

        computeDocumentRefMock().stub {
            on { get() } .thenReturn(futureMock)
        }

        return documentMock.stub(configure)
    }

    internal fun docRef(
        configure: KStubbing<DocumentReference>.(DocumentReference) -> Unit
    ): DocumentReference {
        if (path.size.mod(2) == 1) {
            fail("Incorrect params")
        }

        return computeDocumentRefMock().stub(configure)
    }

    private fun computeDocumentRefMock(): DocumentReference {
        val result = mock<DocumentReference>()

        var documentRefMock = result
        val iterator = path.reversed().iterator()
        var document = iterator.next()

        while (iterator.hasNext()) {
            val collection = iterator.next()
            val collectionRefMock = mock<CollectionReference> {
                on { document(document) }.thenReturn(documentRefMock)
            }

            if (iterator.hasNext()) {
                document = iterator.next()
                documentRefMock = mock {
                    on { collection(collection) }.thenReturn(collectionRefMock)
                }
            } else {
                linkCollection(collection, collectionRefMock)
            }

        }

        return result
    }
}

internal fun Firestore.mock(
    vararg path: String
): MockBuilder {
    return MockBuilder(path.toList()) { name, query ->
        BDDMockito.given(this.collection(name))
            .willReturn(query)
    }
}

