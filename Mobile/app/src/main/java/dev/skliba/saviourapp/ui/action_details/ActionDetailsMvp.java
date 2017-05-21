package dev.skliba.saviourapp.ui.action_details;

import dev.skliba.saviourapp.data.models.response.ActionDetailsResponse;
import dev.skliba.saviourapp.ui.shared.BaseMvp;

public interface ActionDetailsMvp {

    interface Presenter extends BaseMvp.Presenter {

        void init(String actionId);
    }

    interface View extends BaseMvp.View {

        void displayData(ActionDetailsResponse actionDetailsResponse);
    }
}