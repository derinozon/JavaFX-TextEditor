package main.Controllers;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.Models.FileMachine;

import java.io.File;

public class Controller {

	public static File currentFile;


	@FXML
	TextArea notepad;

	@FXML
	TabPane tab_master;

	Tab current_tab;
	TextArea current_text;


	public void initialize () {
		CheckTabs();
	}

	private void CheckTabs () {
		ObservableList<Tab> tabs = tab_master.getTabs();

		if (tabs.size() == 0) {
			tabs.add(NewTab());
		}
	}

	private Tab NewTab () {
		Tab tab = new Tab("untitled ");

		tab.setContent(new TextArea());
		tab_master.getSelectionModel().select(tab);

		tab.setOnSelectionChanged(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				UpdateCurrentTab(tab);
			}
		});
		tab.setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				CheckTabs();
			}
		});
		tab.setOnCloseRequest(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				NewAlert();
			}
		});

		return tab;
	}

	private Boolean NewAlert () {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Confirmation Dialog with Custom Actions");
		//alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		//alert.setContentText("Choose your option.");

		ButtonType b_save = new ButtonType("Save");
		ButtonType b_quit = new ButtonType("Don't Save");
		ButtonType b_cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(b_save,b_quit,b_cancel);
		alert.showAndWait().ifPresent(bt -> {
			if (bt == b_save) {

			}
			else if (bt == b_quit) {

			}
			else {

			}
		});

			return true;
	}

	private void UpdateCurrentTab(Tab tab) {
		current_tab = tab;
		current_text = (TextArea) tab.getContent();
	}
/*

	public void UpdateNotepad () {
		notepad.getStylesheets().clear();

		if (GlobalSettings.getInstance().data.overrideText) {
			try {
				URL url = getClass().getResource("/Main/resources/customText.css");
				notepad.getStylesheets().add(url.toExternalForm());
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
	}


	public void UpdateRoot () {
		ArrayList<ObservableList> array = new ArrayList<>();
		array.add(notepad.getScene().getStylesheets());
		if (settings != null) {
			array.add(settings.Get().getStylesheets());
		}
		if (formatM != null) {
			array.add(formatM.Get().getStylesheets());
		}
		for (ObservableList<String> item : array) {
			item.clear();
		}
		if (GlobalSettings.getInstance().data.overrideTheme) {
			URL url = getClass().getResource("/Main/resources/customTheme.css");
			for (ObservableList<String> item : array) {
				item.add(url.toExternalForm());
			}
		}

	}
*/
	public void FileNew () {
		tab_master.getTabs().add(NewTab());
	}

	public void FileOpen () {
		currentFile = FileMachine.GetFile(Main.getPrimaryStage());

		if (currentFile == null) return;

		current_text.setText(FileMachine.ReadFile(currentFile));

		System.out.print("Opened: " + currentFile.getName());
	}


	public void FileSave () {
		if (currentFile == null) {
			FileSaveAs();
			return;
		}

		FileMachine.WriteFile(currentFile,current_text.getText());

		System.out.print("Saved: " + currentFile.getName());
	}


	public void FileSaveAs () {
		currentFile = FileMachine.WriteFileAs(Main.getPrimaryStage(),current_text.getText());
		current_tab.setText(currentFile.getName());

		System.out.print("Saved as: " + currentFile.getName());
	}


}
