package zenproject.meditation.android.ui.menu.dialogs;

import android.app.DialogFragment;
import android.view.ViewGroup;

import zenproject.meditation.android.ContextRetriever;
import zenproject.meditation.android.R;

public class ZenDialog extends DialogFragment {
    private static final int WIDTH = ContextRetriever.INSTANCE.getResources().getDimensionPixelSize(R.dimen.option_dialog_width);

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
