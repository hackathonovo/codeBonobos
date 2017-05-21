package dev.skliba.saviourapp.ui.action_details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.skliba.saviourapp.R;
import dev.skliba.saviourapp.data.models.response.ActionDetailsResponse;
import dev.skliba.saviourapp.di.MvpFactory;
import dev.skliba.saviourapp.ui.shared.BaseActivity;

public class ActionDetailsActivity extends BaseActivity implements ActionDetailsMvp.View {

    public static final String EXTRA_ACTION_ID = "EXTRA_ACTION_ID";

    @BindView(R.id.leaderPhone)
    TextView leaderPhone;

    @BindView(R.id.actionLocation)
    TextView actionLocation;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.meetingTime)
    TextView meetingTime;

    @BindView(R.id.meetingLocation)
    TextView meetingLocation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String uri;

    private ActionDetailsMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_details);
        ButterKnife.bind(this);

        presenter = MvpFactory.providePresenter(this);

        if (getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra(EXTRA_ACTION_ID))) {
            presenter.init(getIntent().getStringExtra(EXTRA_ACTION_ID));
        } else {
            throw new RuntimeException("ActionId can't be null");
        }
    }

    @Override
    public void displayData(ActionDetailsResponse actionDetailsResponse) {
        toolbar.setTitle("Action #" + actionDetailsResponse.getActionId());
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(v -> supportFinishAfterTransition());

        leaderPhone.setText(actionDetailsResponse.getLeaderPhoneNo());
        actionLocation
                .setText(actionDetailsResponse.getLocation().getLatitude() + " " + actionDetailsResponse.getLocation().getLongitude());

        uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.valueOf(actionDetailsResponse.getLocation().getLatitude()),
                Float.valueOf(actionDetailsResponse.getLocation().getLongitude()));

        description.setText(actionDetailsResponse.getDescription());
        meetingTime.setText(actionDetailsResponse.getMeetingTime());
        meetingLocation.setText(actionDetailsResponse.getMeetingLocation());
    }

    @OnClick(R.id.actionLocation)
    protected void onActionLocationClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
