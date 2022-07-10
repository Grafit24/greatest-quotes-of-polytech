package com.greatestquotes.controllers;

import com.greatestquotes.models.Permissions;
import com.greatestquotes.models.Quote;
import com.greatestquotes.models.Role;
import com.greatestquotes.models.Roles;
import com.greatestquotes.utils.State;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;
import java.time.ZoneId;
import java.util.HashMap;

public class CreateController extends BaseStageController {

    protected Roles allRoles;
    protected HashMap<Role, Permissions> rolePermissionsHashMap = new HashMap<>();

    @FXML
    protected Label headerLabel;

    @FXML
    protected TextArea quoteField;

    @FXML
    protected TextField teacherField;

    @FXML
    protected TextField subjectField;

    @FXML
    protected DatePicker dateField;

    @FXML
    protected Label ownerLabel;

    @FXML
    protected ListView<String> roleList;

    @FXML
    protected CheckBox readChecker;

    @FXML
    protected CheckBox editChecker;

    @FXML
    protected CheckBox deleteChecker;

    @FXML
    protected void onSetButtonClick() {
        ObservableList<String> selectedItems =  roleList.getSelectionModel().getSelectedItems();
        Permissions p = new Permissions(readChecker.isSelected(), editChecker.isSelected(), deleteChecker.isSelected());
        for (String selected : selectedItems) {
            Role role = allRoles.getByName(selected);
            if (role != null)
                rolePermissionsHashMap.put(role, p);
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        headerLabel.setText("Create");
        ownerLabel.setText("");
        allRoles = new Roles();
        allRoles.parse();
        for (Role r: allRoles)
            roleList.getItems().add(r.name());
        roleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // TODO Переделать работу с Date под работу с LocalDate
    @FXML
    protected void onSaveButtonClick() {
        String quote = quoteField.getText();
        String teacher = teacherField.getText();
        String subject = subjectField.getText();
        Date date = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        State state = Quote.createQuote(quote, teacher, subject, date, user, rolePermissionsHashMap);
        if (State.DONE.equals(state))
            user.countAdd(true);
    }

    @FXML
    protected void onRoleSelected() {
        ObservableList<String> selectedItems =  roleList.getSelectionModel().getSelectedItems();
        for (String selected : selectedItems) {
            Role role = allRoles.getByName(selected);
            if (role != null && rolePermissionsHashMap.containsKey(role)) {
                Permissions p = rolePermissionsHashMap.get(role);
                readChecker.setSelected(p.r());
                editChecker.setSelected(p.w());
                deleteChecker.setSelected(p.d());
            } else {
                readChecker.setSelected(false);
                editChecker.setSelected(false);
                deleteChecker.setSelected(false);
            }
        }
    }
}
