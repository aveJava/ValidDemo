package valid.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MyObject {
    @NotEmpty
    @Size(min = 2, message = "должно быть не меньше 2-х символов")
    private String textField = "Пустое поле";

    public String getTextField() {
        return textField;
    }
    public void setTextField(String textField) {
        this.textField = textField;
    }
}
