package com.relevantcodes.extentreports.markuputils;

class CodeBlock implements Markup {
   
    private String code;
    
    public void setCodeBlock(String code) {
        this.code = code;
    }
    public String getCodeBlock() {
        return code;
    }
    
    public String getMarkup() {
        String lhs = "<textarea disabled class='code-block'>";
        String rhs = "</textarea>";
        
        String s = lhs + code + rhs;
        return s;
    }

}
