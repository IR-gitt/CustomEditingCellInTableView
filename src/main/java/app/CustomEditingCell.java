package app;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.SneakyThrows;

import java.lang.reflect.Field;


public class CustomEditingCell<S, T> extends TableCell<S, T> {

    private TextField textField;

    @Override
    public void startEdit() {
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        // включает поле ввода
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }


    @Override
    public void cancelEdit() {
        super.cancelEdit();
        String text = textField.getText();

        // позволяет обновить значение в ячейке без нажатия на ENTER
        updateItem((T) text, textField.getText().isEmpty());
    }


    @SneakyThrows
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            // присавиваем значения в TR
            final TableColumn<S, T> tableColumn = getTableColumn();
            final TableRow<Position> tableRow = getTableRow();
            final Position rowItem = tableRow == null ? null : tableRow.getItem();

            if (rowItem != null) {

                // меняем значения поля
                Class<?> clazz = rowItem.getClass();

                // ищем поле
                Field field = clazz.getDeclaredField(tableColumn.getId());

                // получем тип объекта
                String typeObj = field.getGenericType().getTypeName();

                //instanse of
                if (typeObj.equals("java.lang.String")) {

                    // разрешение доступа
                    field.setAccessible(true);
                    field.set(rowItem, getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                    setText(getString());

                }
            }
        }
    }

    // создается ячейка в строке
    private void createTextField() {
        textField = new TextField(getString());

        // устанавливаем размер текст филда в ячейке
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // установка знач
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit((T) textField.getText());
                }
            }
        });
    }

    // вывод данных из ячейки или нуля
    private String getString() {
        return getItem() == null ? "" : String.valueOf(getItem());
    }
}
