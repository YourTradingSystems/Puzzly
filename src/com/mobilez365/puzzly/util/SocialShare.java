package com.mobilez365.puzzly.util;

import android.content.Context;
import android.os.Bundle;
import com.mobilez365.puzzly.global.Constans;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by andrewtivodar on 27.06.2014.
 */
public class SocialShare {

    public interface ShareListener{
        public void onShareResult(int result);
    }

    private SocialAuthAdapter adapter;
    private ShareListener listener;
    private String message;
    private Context context;
    private SocialAuthAdapter.Provider provider;
    private boolean noInternetConnection;

    public void shareToSocial(Context _context, SocialAuthAdapter.Provider _provider, ShareListener _listener, String _message) {
        adapter = new SocialAuthAdapter(new AuthResponseListener());
        listener = _listener;
        message = _message;
        context = _context;
        provider = _provider;
        noInternetConnection = false;
        adapter.authorize(_context, _provider);
    }

    private final class AuthResponseListener implements DialogListener {
        @Override
        public void onComplete(Bundle bundle) {
            try {
                if(provider == SocialAuthAdapter.Provider.TWITTER)
                    adapter.updateStatus(message, new MessageListener(), false);
                else
                    adapter.updateStory(message,  "Funny Puzzle for Kids", "",
                        "",  "https://play.google.com/store/apps/details?id=com.mobilez365.puzzly",
                    "https://lh5.ggpht.com/SdmoYsckY3EmOOwKKmBnZzYzA247FqNJZKK79sDvlttcJiPqbyR_1zf2hc4MUVNSKeg=w300", new MessageListener());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(SocialAuthError socialAuthError) {
            if(!noInternetConnection) {
                if (socialAuthError.getMessage().equals("Please check your Internet connection")) {
                    listener.onShareResult(Constans.SHARE_INTERNET_ERROR);
                    noInternetConnection = true;
                } else if (socialAuthError.getMessage().equals("Message Not Posted"))
                    listener.onShareResult(Constans.SHARE_POST_DUPLICATE);
                else
                    listener.onShareResult(Constans.SHARE_LOGIN_ERROR);
                adapter.signOut(context, provider.toString());
            }
        }

        @Override
        public void onCancel() {
            listener.onShareResult(-1);
        }

        @Override
        public void onBack() {
            listener.onShareResult(-1);
        }
    }

    private final class MessageListener implements SocialAuthListener<Integer> {
        @Override
        public void onExecute(String s, Integer integer) {
            Integer status = integer;
            if (status.intValue() == 200 || status.intValue() == 201 ||status.intValue() == 204) {
                listener.onShareResult(Constans.SHARE_POST_DONE);
                adapter.signOut(context, provider.toString());
            }
            else {
                listener.onShareResult(Constans.SHARE_POST_ERROR);
                adapter.signOut(context, provider.toString());
            }
        }

        @Override
        public void onError(SocialAuthError socialAuthError) {
            adapter.signOut(context, provider.toString());
            listener.onShareResult(Constans.SHARE_POST_ERROR);
        }
    }

}
