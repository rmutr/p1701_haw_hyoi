package android.in.th.xchange.fragment;

import android.in.th.xchange.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Slump on 12/03/2017.
 */

public class MainFragment extends Fragment{

    // Explicit or Variable Declaration
    private RadioGroup radioGroup;
    private RadioButton usdRadioButton, thdRadioButton;
    private EditText editText;
    private Button button;

    // Create Main Method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initial View
        initialView();

        // Click USD to THB first
        clickUSDToTHBFirst();

    }

    private void initialView() {
        radioGroup = getView().findViewById(R.id.ragMoney);
        usdRadioButton = getView().findViewById(R.id.radUSD);
        thdRadioButton = getView().findViewById(R.id.radTHB);
        editText = getView().findViewById(R.id.edtMoney);
        button = getView().findViewById(R.id.btnExchange);
    }

    private void clickUSDToTHBFirst() {
        usdRadioButton.setChecked(true);
    }

    // Create Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

} // Main Class นี่คือ คลาสหลัก
