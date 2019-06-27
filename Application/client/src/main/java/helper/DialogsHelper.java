package helper;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;
import java.util.function.Supplier;

public class DialogsHelper
{
    public static void showErrorDialog(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occured");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static <R> Optional<R> showCustomDialog(String title, Node content, Supplier<R> dialogDataSupplier)
    {
        Dialog<R> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResizable(true);

        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton.getButtonData() == ButtonBar.ButtonData.OK_DONE)
            {
                return dialogDataSupplier.get();
            }
            return null;
        });
        return dialog.showAndWait();
    }
}
