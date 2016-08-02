package humans.apps.steve.actionmode.interfaces;

import android.view.View;

/**
 * Created by Steve on 2/08/2016.
 */

public interface RecyclerClick_Listener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
