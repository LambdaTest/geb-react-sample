package functionaltest.pages

import geb.Page

class ReactLoginPage extends Page {

    static url = "https://formiz-react.com/docs/demos/wizard"

    static content = {
        nameInput { $("#formiz-field-id-fx8nh8mkw") }
        nickNameInput { $("#formiz-field-id-mwkcy2eck") }
        nextButton { $(".demo-button") }
    }

    void loginForm() {
        // Setting Name value
        setReactValue("formiz-field-id-fx8nh8mkw", "Sushobhit")
        // Setting NickName value
        setReactValue("formiz-field-id-mwkcy2eck", "SD")
        nextButton.click()
    }

    def setReactValue(element, value) {
        String script = '''
        var element = document.getElementById(arguments[0]);
        var value = arguments[1];
        
        var nativeInputValueSetter = Object.getOwnPropertyDescriptor(element.__proto__, "value").set;
        nativeInputValueSetter.call(element, value);

        var customEvent = new Event("input", { bubbles: true});
        element.dispatchEvent(customEvent);
    '''
        js.exec(element, value, script)
    }
}