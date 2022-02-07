package net.dslab.slack.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import com.google.cloud.firestore.WriteResult
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.quarkus.test.junit.mockito.InjectSpy
import net.dslab.firebaseResource
import net.dslab.json
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuth
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.willReturn
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.concurrent.CompletableFuture
import javax.inject.Inject

@QuarkusTest
internal class SlackTeamAuthDaoImplTest {

    @Inject
    private lateinit var firestore: Firestore

    @Inject
    private lateinit var slackTeamAuthDao: SlackTeamAuthDao

    @Test
    internal fun storeAuthInfoOK() {
        val teamId = "team-id"
        val info = SlackTeamAuth("access-token")
        val resultFuture = mock<ApiFuture<WriteResult>>()
        val documentRef = firestore.mock("slack-team", teamId, "auth", "dslab").docRef {
            on { set(any<SlackTeamAuth>(), any()) }.thenReturn(resultFuture)
        }

        slackTeamAuthDao.storeAuthInfo(teamId, info)

        verify(documentRef).set(info, SetOptions.merge())
    }

    @Test
    internal fun getAuthInfoOK() {
        val teamId = "team-id"
        val teamAuth = firebaseResource("slack_team_auth.json").json()

        firestore.mock("slack-team", teamId, "auth", "dslab").docSnapshot {
            on { it.exists() }.thenReturn(true)
            on { it.data }.thenReturn(teamAuth)
        }

        val info = slackTeamAuthDao.getAuthInfo(teamId)

        assertNotNull(info)
        assertEquals("token-value", info?.accessToken)
    }

    @Test
    internal fun getAuthInfoNoK() {
        val teamId = "team-id"
        firestore.mock("slack-team", teamId, "auth", "dslab").docSnapshot {
            on { it.exists() }.thenReturn(false)
        }

        val info = slackTeamAuthDao.getAuthInfo(teamId)

        assertNull(info)
    }
}
