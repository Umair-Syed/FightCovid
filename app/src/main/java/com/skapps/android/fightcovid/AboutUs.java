package com.skapps.android.fightcovid;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null )
         getSupportActionBar().setTitle("About us");

        String description = "Developed by Syed Umair and Irshad Kasana\n"
                + "Logo design by Ankit Kapoor and Zahid Bhat\n"
                + "This project is an open source project under GPLv3 License";


        View aboutPage = new AboutPage(this)
                .setDescription(description)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher_foreground)
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("syedumairandrabi66@gmail.com", "Contact us")
                .addGitHub("Umair-Syed/FightCovid", "Project on GitHub")
                .addInstagram("umairsyed66", "Follow Umair on Instagram")
                .addInstagram("kasana_irshad","Follow Irshad on Instagram")
                .addTwitter("Irshad_kasana6","Follow Irshad on Twitter")
                .addTwitter("UmairSyed10", "Follow Umair on Twitter")
                .create();

        setContentView(aboutPage);

    }
}
