package com.cashpp.cash.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.cashpp.cash.R;
import com.cashpp.cash.activity.MainActivity;
import com.cashpp.cash.db.ReminderDB;
import com.cashpp.cash.model.Reminder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


public class AddRemindersFragment extends BaseFragment {
    private ReminderDB reminder_db;

    private EditText title;
    private EditText value;
    private EditText date;
    private Spinner recurrence;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setTitle(R.string.reminders);
        View view = inflater.inflate(R.layout.fragment_reminders_add, container, false);

        //Botão voltar
        view.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(new RemindersFragment());
            }
        });

        title = (EditText) view.findViewById(R.id.et_title);
        value = (EditText) view.findViewById(R.id.et_value);
        value.addTextChangedListener(new MascaraMonetaria(value));
        date = (EditText) view.findViewById(R.id.et_date);
        recurrence = (Spinner) view.findViewById(R.id.sp_recurrence);

        //Botão criar nova meta
        view.findViewById(R.id.bt_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder_db = new ReminderDB((MainActivity) getActivity());

                Reminder reminder= new Reminder();
                reminder.setTitle(title.getText().toString());
                String value_string = value.getText().toString();
                if (!value_string.isEmpty()) value_string = value_string.substring(2, value_string.length());
                value_string = value_string.replace(".", "");
                value_string = value_string.replaceAll(",", ".");
                reminder.setValue(parseDouble(value_string));
                reminder.setDate(date.getText().toString());
                String recurrence_string = String.valueOf(recurrence.getSelectedItem());
                recurrence_string = recurrence_string.replaceAll("mês", "");
                recurrence_string = recurrence_string.replaceAll("meses", "");
                recurrence_string = recurrence_string.replaceAll("Sem recorrência", "0");
                recurrence_string = recurrence_string.replaceAll(" ", "");
                reminder.setRecurrence(parseInt(recurrence_string));

                long res = reminder_db.saveReminder(reminder);

                if (res != -1) {
                    toast("Lembrete criado com sucesso.");
                } else {
                    toast("Erro ao criar lembrete.");
                }

                ((MainActivity) getActivity()).replaceFragment(new RemindersFragment());
            }
        });

        return view;
    }

    private class MascaraMonetaria implements TextWatcher {

        final EditText campo;

        public MascaraMonetaria(EditText campo) {
            super();
            this.campo = campo;
        }

        private boolean isUpdating = false;
        // Pega a formatacao do sistema, se for brasil R$ se EUA US$
        private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int after) {
            // Evita que o método seja executado varias vezes.
            // Se tirar ele entre em loop
            if (isUpdating) {
                isUpdating = false;
                return;
            }

            isUpdating = true;
            String str = s.toString();
            // Verifica se já existe a máscara no texto.
            boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                    (str.indexOf(".") > -1 || str.indexOf(",") > -1));
            // Verificamos se existe máscara
            if (hasMask) {
                // Retiramos a máscara.
                str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                        .replaceAll("[.]", "");
            }

            try {
                // Transformamos o número que está escrito no EditText em
                // monetário.
                str = nf.format(Double.parseDouble(str) / 100);
                campo.setText(str);
                campo.setSelection(campo.getText().length());
            } catch (NumberFormatException e) {
                s = "";
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Não utilizado
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Não utilizado
        }
    }

}