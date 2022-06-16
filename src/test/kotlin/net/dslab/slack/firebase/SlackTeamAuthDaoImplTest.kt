package net.dslab.slack.firebase

import com.google.cloud.firestore.Firestore
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuthEntity
import net.dslab.utils.firebaseResource
import net.dslab.utils.json
import net.dslab.utils.testcontainers.FirestoreTestResource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(FirestoreTestResource::class)
internal class SlackTeamAuthDaoImplTest {

    @Inject
    private lateinit var firestore: Firestore

    @Inject
    private lateinit var slackTeamAuthDao: SlackTeamAuthDao

    @Test
    internal fun storeAuthInfoOK() {
        val teamId = "team-id"
        val info = SlackTeamAuthEntity("access-token")

        slackTeamAuthDao.storeAuthInfo(teamId, info)

        val actual = firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")
            .get().get()
        assertEquals("access-token", actual.get("accessToken"))
    }

    @Test
    internal fun getAuthInfoOK() {
        val teamId = "team-id"
        val teamAuth = firebaseResource("slack_team_auth.json").json()
        firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")
            .create(teamAuth).get()

        val info = slackTeamAuthDao.getAuthInfo(teamId)

        assertNotNull(info)
        assertEquals("token-value", info?.accessToken)
    }

    @Test
    internal fun getAuthInfoNoK() {
        val teamId = "team-id-not-exists"

        val info = slackTeamAuthDao.getAuthInfo(teamId)

        assertNull(info)
    }
}
