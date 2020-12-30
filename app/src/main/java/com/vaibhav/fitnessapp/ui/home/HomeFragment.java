package com.vaibhav.fitnessapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.vaibhav.fitnessapp.BookAppointment;
import com.vaibhav.fitnessapp.DrawerUser;
import com.vaibhav.fitnessapp.MedicalMyths;
import com.vaibhav.fitnessapp.PayForOrder;
import com.vaibhav.fitnessapp.R;
import com.vaibhav.fitnessapp.Symptoms;
import com.vaibhav.fitnessapp.TypeOfDoctors;
import com.vaibhav.fitnessapp.ViewConfirmAppointments;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView fun;
    ArrayList<String> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        list = new ArrayList<>();
        //add here
        list.add("In the United States alone there are nearly 700,000 physicians. In appreciation of doctors and physicians, National Doctor's Day is celebrated on March 30 every year.");
        list.add("Doctors are just as likely to abuse alcohol and illegal drugs as the average citizen, but are much more likely to abuse prescription drugs due to close proximity and easier procurement. They are also more likely to have a relapse later for the same reasons.");
        list.add("Despite popular belief, doctors are not usually paid to promote a certain drug or device to the patient they are treating. Instead, many doctors may be paid by pharmaceutical companies and device makers to either give talks to groups of patients or doctors about a product, provide advice to companies on clinical trial designs or, less commonly, to conduct research trials at their practices.");
        list.add("About 64% of physicians report working overtime. Some physicians may work as many as 60 hours per week.");
        list.add("Interesting Medical Error Fact\n" +
                "Tools are more likely to be left during emergency surgery or when the procedure changes unexpectedly\n" +
                "Doctors leave sponges and other medical devices inside of their patients about 6,000 times a year.");
        list.add("Researchers suspect that at about 4000 BC the ancient Greeks had the first designated housings for healing the sick. Instead of a formal hospital with physicians, however, they were most likely temples devoted to gods of healing such as Saturn and Asclepius that may or may not have had a separate room serving as a clinic. The sick were dropped off to pray and ask for healing from the gods.");
        list.add("The earliest documentation for a formal hospital with physicians that treated the ill comes from the 5th century BC in Sri Lanka.");
        list.add("Becoming a doctor takes a great amount of schooling—in some cases, as many as 11 years. A physician must first obtain a bachelor's degree, which usually takes 4 years. This is generally followed by 4 years of medical school and then 3 to 8 years of residency, depending upon the specialty chosen.");
        list.add("Acceptance into medical school does not require a certain undergraduate degree, and even English majors have been known to become doctors. As long as the degree program requires classes in biology, chemistry, physics, math, and English, it is usually accepted as preparatory to medical school.");
        list.add("There are two types of physicians: medical doctors (MDs) and doctors of osteopathic medicine (DOs). Both use similar methods of treatment, but DOs place more focus on the musculoskeletal system, preventive medicine, and holistic patient care. DOs are also more likely to be primary care physicians.");
        fun = root.findViewById(R.id.fun);
        Random random = new Random();
        fun.setText(list.get(Math.abs(random.nextInt()) % list.size()));
        return root;
    }

    public void typeOfDoctors(View view) {
        Intent intent = new Intent(getContext(), TypeOfDoctors.class);
        startActivity(intent);
    }

    public void confirmAppointments(View view) {
        Intent intent = new Intent(getContext(), ViewConfirmAppointments.class);
        startActivity(intent);
    }

    public void payForOrder(View view) {
        Intent intent = new Intent(getContext(), PayForOrder.class);
        startActivity(intent);
    }

    public void bookAppointment(View view) {
        Intent intent = new Intent(getContext(), BookAppointment.class);
        startActivity(intent);
    }

    public void medicalMyths(View view) {
        Intent intent = new Intent(getContext(), MedicalMyths.class);
        startActivity(intent);
    }

    public void covidSymptoms(View view) {
        Intent intent = new Intent(getContext(), Symptoms.class);
        startActivity(intent);
    }
}