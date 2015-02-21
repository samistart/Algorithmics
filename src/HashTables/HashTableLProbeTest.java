package HashTables;

public class HashTableLProbeTest {
  public static void main(String[] args) {
    try {
      HashTableLProbe myLUT = new HashTableLProbe(10);

      myLUT.insert("Priscilla", new Integer(41));
      myLUT.insert("Travis", new Integer(34));
      myLUT.insert("Samuel", new Integer(28));
      myLUT.insert("Helena", new Integer(39));
      myLUT.insert("Andrew", new Integer(14));
      myLUT.insert("Kay", new Integer(24));
      myLUT.insert("John", new Integer(67));
      System.out.println(myLUT);

      myLUT.delete("Travis");
      myLUT.delete("John");
      System.out.println(myLUT);

      myLUT.insert("Dani", new Integer(15));
      myLUT.insert("John", new Integer(67));
      myLUT.insert("Travis", new Integer(34));
      System.out.println(myLUT);

      myLUT.insert("Bob", new Integer(52));
      System.out.println(myLUT);

      myLUT.delete2("Travis");
      System.out.println(myLUT);

      myLUT.resize(3);
      System.out.println(myLUT);
      
      myLUT.resize(13);
      System.out.println(myLUT);

    } catch (TableOverflowException e) {
      System.out.println(e);
    } catch (KeyNotFoundInTableException e) {
      System.out.println(e);
    }
  }
}
