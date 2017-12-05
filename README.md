# KotlinBruteForce
Simple library which provides brute-force tools.

## Simple Brute-Forcing
To simply brute-force through all chars in specified charset:

```
  BruteForce(3, "abc123")
                .doOnEach {  
                    println(it.toCharString())
                }  
                .run()   
``` 

Where "3" (`levels`) is length of desired output ByteArray, and "abc123" (`charset`) are chars through which algorithm iterates for every char of output ByteArray.

In `doOnEach` you specify what you want to do with every generated "key". The argument `it` is a ByteArray, each byte is ascii code of each char of key. The lib provides extension functions
for conversions for various types of data. For example `toCharString()` converts ByteArray to plain String of output chars. Normal toString() returns String of hexadecimal values of ByteArray.


`run()` starts the algorithm

The output is:
```
aaa
baa
caa
1aa
2aa
...
b33
c33
133
233
333
```
