/*---------------------------------------------------------------------------------------------
 *  Copyright (c) ggo. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/
package com.gaspargo.passgen;

import java.security.SecureRandom;

/**
 * PassGen:  Generator of Strong & Secure Password, intented to use by command line.
 * 
 * @author ggo 
 * 
 * ported from: pwgen.c
 * https://secure.packetizer.com/pwgen/
 */
public class PassGen {
	
	/** Password length */
	private int passLen = 16;

	/** Include special characters */
	private boolean includeSpecialCharacter = false;

	/** Char used for generate password */
	private char pwchars[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', '~', '`', '!', '@', '#', '$', '%', '^',
        '&', '*', '(', ')', '_', '+', '=', '-', '{', '}', '|', '\\', ']', '[',
        ':', '"', '\'', ';', '<', '>', '?', '/', '.'
    };

	/**
	 * To avoid modulo bias in selecting random numbers, we will discard
	 * any random number greater than max_random. This value of max_random
	 * is determined as per the equations shown below.
	 */	
    private int maxRandom() {

        if (includeSpecialCharacter) {
            return 255 - (255 % 93) - 1;
        } else {
            return 255 - (255 % 62) - 1;
        }
    }
	
	/**
	 * Generate the password with character and the length specified.
	 * 
	 * @return the generated password
	 */
    public String generatePassword() {
    	
    	SecureRandom rand = new SecureRandom();
    	
    	int maxRandom = maxRandom();
    	
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < passLen; i++) {
    		int randInt = rand.nextInt(maxRandom);
    		
    	    if (includeSpecialCharacter) {
    	    	sb.append(pwchars[randInt % 93]);
    	    } else {
    	    	sb.append(pwchars[randInt % 62]);
    	    }    
    	}
    	
    	return sb.toString();
    }
    
    public void testMaxRandom() {

    	int mr = maxRandom();
		System.out.println("maxRandom(true) " + mr);
		
		mr = maxRandom();
		System.out.println("maxRandom(true) " + mr);
    }
	
    public void testGeneratePassword() {
    	
		System.out.println("Lenght pwchars[] = " + pwchars.length);
		for (int i = 0; i < 6; i++) {
			System.out.println(":: Generate Password :: ");
			String pwd = generatePassword();
			System.out.println(pwd);
			System.out.println("---------------------------------");
		}
    }
	
	public static void main(String[] args) {
		
		// TODO: Parsear argumentos con JavaCmdLineParser(creare)
		
		PassGen passGen = new PassGen();

		passGen.testGeneratePassword();
	}
	
	/**
	 * @return the passLen
	 */
	public int getPassLen() {
		return passLen;
	}

	/**
	 * @param passLen the passLen to set
	 */
	public void setPassLen(int passLen) {
		this.passLen = passLen;
	}

	/**
	 * @return the includeSpecialCharacter
	 */
	public boolean isIncludeSpecialCharacter() {
		return includeSpecialCharacter;
	}

	/**
	 * @param includeSpecialCharacter the includeSpecialCharacter to set
	 */
	public void setIncludeSpecialCharacter(boolean includeSpecialCharacter) {
		this.includeSpecialCharacter = includeSpecialCharacter;
	}

	
}