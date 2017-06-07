package org.vedibarta.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.vedibarta.app.R;
import org.vedibarta.app.network.DownloadManager;
import org.vedibarta.app.network.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.vedibarta.app.network.RetrofitHelper.BASE_URL_VEDIBARTA;

/**
 * Created by Yaakov Shahak
 on 07/06/17.
 */

@SuppressWarnings("unused")
public class FragmentFeedback extends Fragment {

    @BindView(R.id.EditTextName)
    EditText EditTextName;
    @BindView(R.id.EditTextEmail)
    EditText EditTextEmail;
    @BindView(R.id.EditTextFeedbackBody)
    EditText EditTextFeedbackBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.feedback2, container, false);
        ButterKnife.bind(mainView);
        return mainView;
    }

    @OnClick(R.id.link_vedibarta)
    void openVedibartaSite() {
        Uri uri = Uri.parse(BASE_URL_VEDIBARTA);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.button_send_feedback)
    void sendFeedback() {
        if (Utils.isConnected(getContext())) {
            String name = EditTextName.getText().toString();
            String mail = EditTextEmail.getText().toString();
            String feedback = EditTextFeedbackBody.getText().toString();

            if (name.equals("") || mail.equals("") || feedback.equals("")) {
                Toast.makeText(getContext(), getResources().getString(R.string.missing_parameters), Toast.LENGTH_SHORT).show();
            } else if (!Utils.isValidEmail(mail))
                Toast.makeText(getContext(), getResources().getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
            else {
                DownloadManager.sendFeedback(getContext(), name, mail, "נשלח מתוך אפליקצית 'ודיברת' לאנדרואיד:    " + feedback);
                Toast.makeText(getContext(), getString(R.string.comment_success),
                        Toast.LENGTH_SHORT).show();
                EditTextName.setText("");
                EditTextEmail.setText("");
                EditTextFeedbackBody.setText("");

            }
        } else
            Toast.makeText(getContext(), getResources().getString(R.string.comment_no_network), Toast.LENGTH_SHORT).show();
    }
}