/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.personal.comunitarias.noticias;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personal.comunitarias.R;
import com.squareup.picasso.Picasso;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        final Noticia noticia = CardContentFragment.ViewHolder.select;

        Resources resources = getResources();
        collapsingToolbar.setTitle(noticia.getTitulo());
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(noticia.getDescripcion());

        TextView placeLocation = (TextView) findViewById(R.id.place_location);
        placeLocation.setText(noticia.getFecha());
        ImageView placePicutre = (ImageView) findViewById(R.id.image);

        if (noticia.getS_img().isEmpty()) {
            Picasso.with(this).load("http://ecuadoruniversitario.com/wp-content/uploads/2015/02/consejo-participacion-ciudadana-cpccs.jpg").into(placePicutre);
        } else
            Picasso.with(this).load(noticia.getS_img()).into(placePicutre);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticiaWebView(noticia);
            }
        });

    }
    public void noticiaWebView(Noticia noticia) {

        pd = new ProgressDialog(this);
        pd.setMessage("Cargando...");
        WebView myWebView = new WebView(this);

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl(noticia.getUrl());


        new AlertDialog.Builder(this).setView(myWebView)
                .setTitle(noticia.getTitulo())
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
        pd.show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (pd.isShowing()) {
                pd.cancel();

            }

        }
    }



}
