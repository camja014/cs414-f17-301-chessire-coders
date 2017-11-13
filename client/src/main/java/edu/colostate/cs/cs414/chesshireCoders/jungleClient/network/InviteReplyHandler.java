package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InviteReplyRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InviteReplyResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import javafx.application.Platform;

public class InviteReplyHandler extends Listener {

	private InviteReplyRequest inviteReplyRequest;	
	
	public InviteReplyHandler() {
		
	}
	
	public void sendAcceptInvite(Invitation invite) {
		inviteReplyRequest = new InviteReplyRequest(AuthTokenManager.getInstance().getToken(), true, invite.getInvitationId());
		App.getJungleClient().sendMessage(inviteReplyRequest);
	}
	
    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof InviteReplyResponse) {
            handleAcceptedInviteResponse((InviteReplyResponse) received);
        }
    }

	private void handleAcceptedInviteResponse(InviteReplyResponse response) {
		// JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
        	// do stuff
        	
        	App.getJungleClient().removeListener(this);
        });
	}
	
	public void sendRejectInvite(Invitation invite) {
		inviteReplyRequest = new InviteReplyRequest(AuthTokenManager.getInstance().getToken(), false, invite.getInvitationId());
		App.getJungleClient().sendMessage(inviteReplyRequest);
	}
}