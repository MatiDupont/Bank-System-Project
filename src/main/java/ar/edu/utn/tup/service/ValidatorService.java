package ar.edu.utn.tup.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorService {
    /*
        . (punto) coincide con cualquier carácter excepto saltos de línea.
        * significa "cero o más" del elemento anterior.
        + significa "uno o más" del elemento anterior.
        {n} especifica exactamente n repeticiones del elemento anterior.
        {n,} especifica al menos n repeticiones del elemento anterior.
        {n,m} especifica entre n y m repeticiones del elemento anterior.
        [abc] coincide con cualquiera de los caracteres a, b o c.
        [a-z] coincide con cualquier carácter en el rango a a z.
    */

     /* --> DNI_PATTERN <--
     ^ (indica inicio de la cadena)
     \\d (coincide con cualquier digito (0-9))
     {8} (especifica que debe haber exactamente 8 digitos)
     $ (indica el fin de la cadena)
     */
    private static final Pattern DNI_PATTERN = Pattern.compile("^\\d{8}$");

    /* --> EMAIL PATTERN <--
    ^ (indica inicio de la cadena)
    [A-Za-z0-9+_.-] (coincide con uno o mas caracteres alfanumericos(A-Za-z0-9), mas(+), subrayado(_), punto(.) o guion(-))
    @ (debe contener un simbolo arroba)
    [A-Za-z0-9.-] (coincide con uno o mas caracteres alfanumericos(A-Za-z0-9), punto(.) o guion(-))
    \\. (asegura que haya un punto(.) despues de los caracteres anteriores
    [A-Za-z]{2,} (coincide con al menos dos caracteres alfabeticos despues del punto, para representar el dominiio de nivel superior (TLD)
    $ (indica el fin de la cadena)
    */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /* --> WEAK PASSWORD PATTERN <--
    ^ (indica inicio de la cadena)
    (?=.*[a-zA-Z]) (asegura que haya al menos un caracter alfabetico(mayuscula o minuscula))
    .{6,} (la contraseña debe tener al menos 6 caracteres de cualquier tipo)
    $ (indica el fin de la cadena)
    */
    private static final Pattern WEAK_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z]).{6,}$");

    /* --> MEDIUM PASSWORD PATTERN <--
    ^ (indica inicio de la cadena)
    (?=.*[a-zA-Z]) (asegura que haya al menos un caracter alfabetico(mayuscula o minuscula))
    (?=.*\\d) (asegura que haya al menos un digito)
    .{6,} (la contraseña debe tener al menos 6 caracteres de cualquier tipo)
    $ (indica el fin de la cadena)
    */
    private static final Pattern MEDIUM_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");

    /* --> STRONG PASSWORD PATTERN <--
    ^ (indica inicio de la cadena)
    (?=.*[a-zA-Z]) (asegura que haya al menos un caracter alfabetico(mayuscula o minuscula))
    (?=.*\\d) (asegura que haya al menos un digito)
    (?=.*[@#$%^&+=]) (asegura que haya al menos un caracter especial entre @#$%^&+=)
    .{8,} (la contraseña debe tener al menos 8 caracteres de cualquier tipo)
    $ (indica el fin de la cadena)
    */
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");

    /* --> DATE_PATTERN <--
     ^ (indica inicio de la cadena)
     \\d (coincide con cualquier digito (0-9))
     {4} (especifica que debe haber exactamente 4 digitos)
     / (especifica que debe haber una barra)
     \\d (coincide con cualquier digito (0-9))
     {2} (especifica que debe haber exactamente 2 digitos)
     / (especifica que debe haber una barra)
     \\d (coincide con cualquier digito (0-9))
     {2} (especifica que debe haber exactamente 2 digitos)
     $ (indica el fin de la cadena)
     */
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}/\\d{2}/\\d{2}");

    /* --> NAME PATTERN <--
    ^ (indica inicio de la cadena)
    [A-Z] (asegura que la primera letra sea una mayuscula)
    [a-z]* (permite cualquier numero de letras minusculas despues de la mayuscula inicial)
    (?: [A-Z][a-z]*)* (es un grupo no capturante que permite cero o mas repeticiones de un espacio seguido de una letra mayuscula y cualquier numero de letras minusculas)
    $ (indica el fin de la cadena)
    */
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-z]*(?: [A-Z][a-z]*)*$");

    /* --> ADDRESS PATTERN <--
    ^ (indica inicio de la cadena)
    [A-Z] (asegura que la primera letra sea una mayuscula)
    [a-z]* (permite cualquier numero de letras minusculas despues de la mayuscula inicial)
    (?: [A-Z][a-z]*)* (es un grupo no capturante que permite cero o mas repeticiones de un espacio seguido de una letra mayuscula y cualquier numero de letras minusculas)
    \\d+ (asegura que haya un espacio seguido por uno o mas digitos)
    $ (indica el fin de la cadena)
    */
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Z][a-z]*(?: [A-Z][a-z]*)* \\d+$");

    /* --> DATE_PATTERN <--
     ^ (indica inicio de la cadena)
     \\+ (asegura que haya un simbolo mas(+) al inicio)
     \\d{1,3} (permite de 1 a 3 digitos)
     \\d (asegura un unico digito)
     \\d{10} (permite exactamente 10 digitos)
     $ (indica el fin de la cadena)
     */
    private static final Pattern TELEPHONE_PATTERN = Pattern.compile("^\\+\\d{1,3} \\d \\d{10}$");

    /* --> SECURITY_CODE_PATTERN <--
     ^ (indica inicio de la cadena)
     \\d{4,6} (permite de 4 a 6 digitos)
     $ (indica el fin de la cadena)
     */
    private static final Pattern SECURITY_CODE_PATTERN = Pattern.compile("^\\d{4,6}$");

    /* --> ALIAS_PATTERN <--
    ^ (indica inicio de la cadena)
    (?:[a-z0-9]+\.){1,2} (secuencia de caracteres alfanuméricos seguidos por un punto, que puede repetirse una o dos veces)
    [a-z0-9]+ (secuencia de caracteres alfanuméricos al final de la cadena.)
    $ (indica el fin de la cadena)
    */
    private static final Pattern ALIAS_PATTERN = Pattern.compile("^(?:[a-z0-9]+\\.){1,2}[a-z0-9]+$");

    public static boolean isValidNID(String NID) {
        if (NID == null) {
            return false;
        }

        Matcher matcher = DNI_PATTERN.matcher(NID);

        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null){
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);

        return matcher.matches();
    }

    public static String getPasswordStrength(String password) {
        if (password == null) {
            return "Invalid";
        }
        if (STRONG_PASSWORD_PATTERN.matcher(password).matches()) {
            return "Strong";
        }
        else if (MEDIUM_PASSWORD_PATTERN.matcher(password).matches()) {
            return "Medium";
        }
        else if (WEAK_PASSWORD_PATTERN.matcher(password).matches()) {
            return "Weak";
        }
        else {
            return "Invalid";
        }
    }

    public static boolean isValidDate(String birthday) {
        if (birthday == null) {
            return false;
        }

        Matcher matcher = DATE_PATTERN.matcher(birthday);

        return matcher.matches();
    }

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        Matcher matcher = NAME_PATTERN.matcher(name);

        return matcher.matches();
    }

    public static boolean isValidAddress(String address) {
        if (address == null) {
            return false;
        }

        Matcher matcher = ADDRESS_PATTERN.matcher(address);

        return matcher.matches();
    }

    public static boolean isValidTelephone(String telephoneNumber) {
        if (telephoneNumber == null) {
            return false;
        }

        Matcher matcher = TELEPHONE_PATTERN.matcher(telephoneNumber);

        return matcher.matches();
    }

    public static boolean isValidSecurityCode(String code) {
        if (code == null) {
            return false;
        }

        Matcher matcher = SECURITY_CODE_PATTERN.matcher(code);

        return matcher.matches();
    }

    public static boolean isValidAlias(String alias) {
        if (alias == null) {
            return false;
        }

        Matcher matcher = ALIAS_PATTERN.matcher(alias);

        return matcher.matches();
    }
}
