package functionaltest.specs

import functionaltest.pages.ReactLoginPage

class ReactLoginSpec extends BasePageGebSpec {

    def "react login wizard form"() {
        when:
        to ReactLoginPage

        and:
        loginForm()

        then:
        $(".docTitle_2AEX").text() == "Wizard Form"
    }

}