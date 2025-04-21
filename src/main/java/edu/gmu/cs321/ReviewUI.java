package edu.gmu.cs321;/*
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReviewUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        // IMMIGRANT VARIABLES
        String name = "Joel Ballard";
        String birthdate = "03/05/1994";
        String address = "12345 Nowhere Lane";
        String email = "JoelBall.email.com";

        //PETITION VARIABLES
        String petitionID = "P00112233";
        String petitionerID = "U9876543";
        String submissionDate = "04/04/2025";
        String status = "SUBMITTED";

        //OBJECT CREATION (MIGHT NOT USE)
        Immigrant immigrant = new Immigrant(name, birthdate, address, email);
        Petition petition = new Petition(petitionID, immigrant.getUserID(), submissionDate, status);


        primaryStage.setTitle("Petition Review");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Page Title
        Text scenetitle = new Text("Petition for Alien Fiancé(e) and Children");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 1, 2, 1);


        //--------------------------------------------------------------------------------//
        //                          IMMIGRANT INFORMATION                                 //
        //--------------------------------------------------------------------------------//


        Label Spacer01 = new Label("");
        grid.add(Spacer01, 0, 2, 2, 2);

        Label immigrantTitle = new Label("---- Immigrant (Petitioner) Information ----");
        //immigrantTitle.setFont();
        grid.add(immigrantTitle, 0, 3, 2, 2);


        Label immigrantNameTitle = new Label("Name:");
        grid.add(immigrantNameTitle, 0, 5);
        TextField immigrantName = new TextField(name);
        grid.add(immigrantName, 1, 5);

        Label immigrantBDTitle = new Label("Birthdate:");
        grid.add(immigrantBDTitle, 0, 6);
        TextField immigrantBD = new TextField(birthdate);
        grid.add(immigrantBD, 1, 6);

        Label immigrantAddressTitle = new Label("Address:");
        grid.add(immigrantAddressTitle, 0, 7);
        TextField immigrantAddress = new TextField(address);
        grid.add(immigrantAddress, 1, 7);

        Label immigrantEmailTitle = new Label("Email:");
        grid.add(immigrantEmailTitle, 0, 8);
        TextField immigrantEmail = new TextField(email);
        grid.add(immigrantEmail, 1, 8);


        //--------------------------------------------------------------------------------//
        //                          PETITION INFORMATION                                  //
        //--------------------------------------------------------------------------------//


        Label Spacer02 = new Label("");
        grid.add(Spacer02, 0, 9, 2, 2);

        Label petitionTitle = new Label("------------ Petition Information ------------");
        grid.add(petitionTitle, 0, 11, 2, 2);

        Label petitionIDTitle = new Label("PetitionID:");
        grid.add(petitionIDTitle, 0, 13);
        TextField petitionIDAns = new TextField(petitionID);
        grid.add(petitionIDAns, 1, 13);

        Label petitionerIDTitle = new Label("PetitionerID:");
        grid.add(petitionerIDTitle, 0, 14);
        TextField petitionerIDAns = new TextField(petitionerID);
        grid.add(petitionerIDAns, 1, 14);

        Label submissionDateTitle = new Label("Submission Date:");
        grid.add(submissionDateTitle, 0, 15);
        TextField submissionDateAns = new TextField(submissionDate);
        grid.add(submissionDateAns, 1, 15);

        Label statusTitle = new Label("Status:");
        grid.add(statusTitle, 0, 16);
        TextField statusAns = new TextField(status);
        grid.add(statusAns, 1, 16);


        //--------------------------------------------------------------------------------//
        //                                    Buttons                                     //
        //--------------------------------------------------------------------------------//


        Label Spacer03 = new Label("");
        grid.add(Spacer03, 0, 17, 2, 2);

        Button denialButton = new Button("<");
        grid.add(denialButton, 0, 18);

        Button saveButton = new Button("save");
        grid.add(saveButton, 1, 18);

        Button approvalButton = new Button("Approval");
        grid.add(approvalButton, 2, 18);


        Scene scene = new Scene(grid, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
