package services;

import utils.ApplicationUtilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.Normalizer;

public class HashService {
    public static String hash( String text )
    {
        try
        {
            return new BigInteger( 1, MessageDigest.getInstance( "MD5" ).digest( text.getBytes( "UTF-8" ) ) ).toString( 16 );
        }

        catch ( Exception e )
        {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return "(FAIL)";
    }

    public static String normalize( String text )
    {
        return Normalizer.normalize( text, Normalizer.Form.NFKD ).replaceAll( "\\p{InCombiningDiacriticalMarks}+", "" );
    }
}
