/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import org.passay.AbstractDictionaryRule;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

/**
 *
 * @author elvis
 */
public class UniqueTokenGenerator {
    
    public static String generateUniqueVerificationToken(){
        PasswordGenerator generator = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        
        lowerCaseRule.setNumberOfCharacters(3);
        
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        
        upperCaseRule.setNumberOfCharacters(3);
        
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitCaseRule = new CharacterRule(digitChars);
        
        digitCaseRule.setNumberOfCharacters(3);
        
        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return AbstractDictionaryRule.ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        
        CharacterRule specialCaseRule = new CharacterRule(specialChars);
        
        specialCaseRule.setNumberOfCharacters(3);
        
        String uniqueToken = generator.generatePassword(12, specialCaseRule,upperCaseRule,digitCaseRule,lowerCaseRule);
        return uniqueToken;
        
    }
    
}
