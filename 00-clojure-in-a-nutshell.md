# References

http://lifeofaprogrammergeek.blogspot.com/2009/03/learning-clojure-and-emacs.html, 

http://www.moxleystratton.com/article/clojure/for-non-lisp-programmers - a little dated?


# History of Clojure is the History of Lisp 

Clojure is a modern variant of Lisp-1. The following comparisons are to the "original" Lisp.

As in Lisp, code and data have the same syntax.
Clojure has a lot more syntactic sugar than Lisp, which makes it more complex to learn but easier to read.
Clojure supports functional programming, but does not encourage it to the extent that Lisp does.
Clojure supports lock-free concurrency with Software Transactional Memory.
Like many modern languages, Clojure is interoperable with Java and uses the JVM.
Basic data types

Numbers: Clojure has integers, floating point numbers, and ratios.

Integers include decimal integers (255), octals numbers (012), hexadecimal numbers (0xff) and radix numbers (2r1111), where the radix is an integer between 2 and 36, inclusive.
Floating point numbers include standard notation (3.1416) and scientific notation(1.35e-12).
Ratios are "fractions" that are not equivalent to integers, such as 1/3. They are not subject to roundoff error.
Atoms (variables): Names for values. Clojure allows most non-alphanumeric characters in names (but not parentheses, brackets, or braces).

Important atoms: true, false, and nil. In a condition, nil behaves like false; but nil is not equal to an empty list, ().

Strings are enclosed in double quotes, and can contain escaped characters: "hello", "line one\nline two".

Regular expressions are written as a hash character in front of a string: #"[a-z]+". Backslashes do not need to be doubled.

Keywords are not reserved words (like while in Java), but are like "atoms" in Prolog or Erlang. A keyword begins with a colon (:foo) and stands for itself. Keywords are often used as keys in maps.

A list is a sequence of values, separated by whitespace (including commas) and enclosed in parentheses.

Function calls are written as a list.
The first element of a list is the name of a function, and the remaining elements are the arguments to the function, for example (max 23 92 17).
Lists that don't represent function calls should usually be quoted.A list can be quoted with the syntax '(1 2 3) or (quote (1 2 3)).
A vector is a sequence of values enclosed in brackets, for example [1 2 "buckle my shoe"], or equivalently, (vector 1 2 "buckle my shoe"). Elements of a vector are separated by whitespace, including commas. Vectors are used to enclose the formal parameters of a function.

A map or hash is a sequence of key/value pairs, enclosed in braces, for example, {:ace 1, :deuce 2, "trey" 3}. Elements are separated by whitespace or commas; it is helpful to use commas between key/value pairs.

A set is a pound sign (#) followed sequence of values enclosed in braces, for example #{a b c}.

Clojure has functions, special forms, and macros.

As in other languages, the arguments to a function are evaluated, and these values are passed to the function
The arguments to a special form are not evaluated, but are passed in "as is" to the special form, which decides when and whether to evaluate them. There are only a small number of special forms, and they are all important.
A macro takes its arguments unevaluated, and returns an expression to be evaluated in place of the call to the macro. Macros allow the programmer to "extend" the language.
Functions are first-class objects. That is, a function is a value, and can be treated like other kinds of values. Functions can be assigned to variables, passed as parameters to functions, returned as the result of functions, and kept in collections, and there are operations that act on functions. Special forms and macros are not first-class objects.

Comments begin with a semicolon and extend to the end of the line.

Defining and calling functions

The syntax of a named function is: (defn function_name [arguments] expressions). The value of the function is the value of the last expression evaluated.

A function may be defined to evaluate different expressions based on the number of arguments. The syntax is (defn function_name ([arguments] expressions) ... ([arguments] expressions)), where each list of arguments is a different length.

A function may be defined to accept an arbitrary number of arguments. The syntax is (defn function_name [arguments & last-argument] expressions). The arguments before the ampersand are as usual; the one last-argument is given to the function as a list of all remaining arguments.

A documentation string may be placed immediately before the vector of arguments. The function (doc function-name) will print out this documentation string, along with other information about the function. doc can be used with built-in functions.

The syntax of a function call is (function_name arguments). The name of the function being called is the first thing inside the parentheses.

The syntax of an anonymous function, or lambda, is (fn [arguments] expressions). This syntax can be used in place of a function name; for example, ((fn [lst] (first lst)) my-list) is equivalent to (first my-list).

Built-in functions

Notation: A + suffix on an argument means "one or more"; a * suffix means "zero or more"; and a ? suffix means "zero or one", that is, optional.
Absolutely fundamental functions

(first sequence)
Returns the first element of a nonempty sequence, or nil if the sequence is empty. For "unordered" collection types, such as sets, some element will be returned, based on how the collection is stored in memory.
(restsequence)
Returns a sequence containing all the elements of the given sequence but the first, or () if the given sequence is empty.
(cons value sequence)
Returns a new sequence created by adding the value as the first element of the given sequence.
(empty?sequence)
Returns true if the list is empty, and false otherwise.
 
(= value1 value2)
Tests whether two values are equal. Works for just about anything except functions.
Macros and special forms

Macros and special forms differ from functions in that the arguments are not evaluated before the special form is called. They can then decide whether, when, and how often to evaluate the arguments. There is little difference from the user's point of view; but new macros can be written by the programmer.

(quote argument)
Returns its argument, unevaluated.
(def name expression)
Defines the name to have the value of the expression, in the current namespace. Clojure is single-assignment: Names cannot be reassigned to have a different value (except in the REPL).
(if test thenPart elsePart?)
The test is evaluated; if it is a "true" value, the thenPart is evaluated; if it is a "false" value and there is an elsePart, the elsePart is evaluated. This special form is convenient for simple tests, but for multiway tests, cond is preferable.
(when test expression ... expression)
If the test evaluates to a true value, the expressions are evaluated, and the value of the last expression is returned.
(cond test expression ... test expression)
The tests are evaluated in order, until one is found that results in a "true" value; then the corresponding expression is evaluated and returned as the value of the cond. If no test is true, nil is returned.
(do exprs*)
Evaluates the expressions in order and returns the value of the last. If no expressions are supplied, returns nil.
(let [name value ... name value] expressions*)
Defines local names, evaluates the corresponding values, and binds them to the names. The names are then available in the expressions, which are evaluated in order. The value of the let is the value of the last expression evaluated.
(throw expression)
The expression must evaluate to some kind of Throwable, which is then thrown.
(try expressions* catch-clauses* finally-clause?)
Evaluates the expressions. If no exceptions occur and the finally-clause is omitted, the result of the last expression is returned. If an exception occurs, then the corresponding catch-clause, with the syntax (catch ExceptionName name expressions*), is evaluated and returned. If there is a finally-clause, with the syntax (finally expressions*), then whether or not an exception occurs, it will be evaluated for its side effects.
(recur expressions*)
Performs a recursive call from the tail position (see Tail Recursion), with the exact (not arbitrary) number of arguments.
(loop [bindings*] expressions*)
Like recur, but the recursion point is at the top of the loop.
 
 
 
Functions for specific data types

Characters and strings

Clojure depends on Java for almost all character and string manipulations, for example, (.toUpperCase string) and (Character/toUpperCase char).

(str arguments*)
Converts any number of arguments into strings and concatenates them; ignores any nil argument. Also useful if the arguments are already strings.
Vectors

A vector is a sequence of values enclosed in brackets.

A vector can be created with (vector args*), or by writing the values in square brackets.
To access an element by its position, use (nth vector index) or (nth vector index not-found).
(rseq vector) returns, in constant time, a sequence of the items in the vector, in reverse.
(pop vector) returns a vector without the last element.
(subvec vector start end) returns a view of the vector from start up to but not including end.
(replace map vector) replaces the keys found in the vector with the corresponding values.
(assoc vector index value) returns a new vector with the index-th location set to the value.
Maps (also known as hashes)

A map or hash is a sequence of key/value pairs, enclosed in braces. The primary operation is searching a map for a given key, and returning its value. This can be done by using either the map or a key as a function, or the function get.

(map key)
(key map)
(get map key)
(get map key default-value)
Each of these looks up the key in the map, and returns the associated value. If the key does not occur in the map, the first and third forms return nil, while the third form is illegal and the fourth form returns the default-value.
(keys map)
Returns a list of the keys.
(vals map)
Returns a list of the values.
(contains? map key)
Tests whether the key is in the map.
 
Records

A record definition is something like a Java class. It acts as a map but, because the "fields" are predefined, it can be more efficient.

To define a record type: (defrecord TypeName [fieldnames*]). Example: (defrecord Tree [value left right]). Don't use keywords as field names.
To create an instance: (Typename. args*). Example: (def animals (Tree. "Water animal?" "frog" "horse")).
To refer to a field, use the keyword version of the fieldname as a function: for example, (:left animals).
Structs

Records are newer and better than structs. Don't use structs.

Arithmetic

+   Returns the sum of its arguments; (+) returns 0
-   Subtracts the rest of the numbers from the first number
*   Returns the product of its arguments; (*) returns 1.
/   Divides the rest of the numbers into the first number
If operands are integers, result is a ratio
If only one number, returns its inverse
quot   Returns the quotient of integer division of the first number by the rest of the numbers
rem    Remainder of dividing the first number by the second
mod    Modulus of first and second number; truncates toward negative infinity
inc    Returns a number one greater than its argument
dec    Returns a number one less than its argument
max    Returns the largest of its arguments
min    Returns the smallest of its arguments
Numeric comparisons

==   Returns true if numeric arguments all have the same value, otherwise false
not= Same as (not (= obj1 obj2))
<    Returns true if numeric arguments are in monotonically increasing order
>    Returns true if numeric arguments are in monotonically decreasing order, otherwise false
<=   Returns true if numeric arguments are in monotonically non-decreasing order, otherwise false
>=   Returns true if numeric arguments are in monotonically non-increasing order, otherwise false
Tests

nil?       Returns true if the argument is nil, false otherwise
identical? Tests if the two arguments are the same object
zero?      Returns true if the argument is zero, else false
pos?       Returns true if the argument is greater than zero
neg?       Returns true if the argument is less than zero, else false
even?      Returns true if the argument is even, throws an exception if the argument is not an integer
odd?       Returns true if n is odd, throws an exception if the argument is not an integer
coll?      Returns true if the argument implements IPersistentCollection
seq?       Return true if the argument implements ISeq
vector?    Return true if the argument implements IPersistentVector
list?      Returns true if the argument implements IPersistentList
map?       Return true if the argument implements IPersistentMap
set?       Returns true if the argument implements IPersistentSet
contains?  Returns true if key is present in the given collection, else false
distinct?  Returns true if no two of the arguments are =
empty?     Returns true if the collection argument has no items - same as (not (seq coll))
Use the idiom (seq x) rather than (not (empty? x))
Higher-order functions

A higher-order function is one that accepts a function as an argument, returns a function as its result, or both.

(map function sequence) Applies the single-parameter function to each value in the sequence, returning a list of the results.
(filter test sequence) Applies the test to each value in the sequence and returns a list of the values that pass the test.
reduce
every?     Returns true if the first argument (a predicate) is logically true for every element in the second argument (a collection), else false
not-every? Returns false if the first argument (a predicate) is logically true for every element in the second argument (a collection), else true
some       Returns the first value in the second argument (a collection) that satisfies the first argument (a predicate)
not-any?   Returns true if no value in the second argument (a collection) satisfies the first argument (a predicate)
(for [name sequence :when test] expression) Sets the name to each value in the sequence, omitting those for which the (optional) :when test is false, evaluates the expression using this value, and returns a list of the results.
Macros

Metaprogramming is using a program to write a program (or parts of a program). Metaprogramming is especially in a homoiconic language, that is, a language in which code and data have the same representation. Clojure is homoiconic.

A Clojure macro resembles a function, with these important differences:

The arguments to a macro are not evaluated
The result returned by the macro must be Clojure code
Calls to the macro are executed at compile time, and the call is replaced by the result that is returned.
The following examples are from Practical Clojure by Luke VanderHart and Stuart Sierra.

(defmacro triple-do [form] ; makes three copies of the form
    (list 'do form form form) )
The call (triple-do (print "Hello ")) prints "Hello Hello Hello " and returns nil.

The forms macroexpand and macroexpand-1 each take a quoted macro call, and return the expanded macro; macroexpand-1 expands a recursive macro only once. For example, (macroexpand '(triple-do (print "Hello "))) returns (do (print "Hello ") (print "Hello ") (print "Hello ")).

Templates

The usual list operations are enough to create code, but can be very cumbersome. To make this easier, Clojure supplies a special kind of quote, `, called a syntax-quote. What makes this special is that within the quoted code, things can be unquoted with ~. For example,

(defmacro triple-do [form] ; makes three copies of the form
    `(do ~form ~form ~form) )
If s is a sequence, ~s will be put into the template as the entire sequence. Sometimes it is desirable to instead put the individual elements of the sequence into the template. This can be done with the splicing unquote, ~@ -- for example, ~@s. If this doesn't make sense now, come back to it when you get unwanted parentheses in your expanded macro.

To avoid name conflicts with the code that uses a macro, it is illegal to create and bind local symbols in a macro, unless you end the symbol name with a # character. This tells Clojure to replace the symbol name with some generated name that is guaranteed to be unique. For example, if you define

(defmacro average-macro [a b]
  `(let [avg# (+ ~a ~b)] (/ avg# 2)) )
then (macroexpand '(average-macro 3 5)) will return something like (let* [avg__99__auto__ (clojure.core/+ 3 5)] (clojure.core// avg__99__auto__ 2)).
Input/Output

*in*      A java.io.Reader object representing standard input for read operation
*out*     A java.io.Writer object representing standard output for print operations
*err*     A java.io.Writer object representing standard error for print operations
print     Prints the object(s) to the output stream that is the current value of *out*
printf    Prints formatted output, as per format
println   Same as print followed by (newline)
pr        Prints the object(s) to the output stream that is the current value of *out*
prn       Same as pr followed by (newline). Observes *flush-on-newline*
newline   Writes a newline to the output stream that is the current value of *out*
read-line Reads the next line from stream that is the current value of *in*
slurp     Reads the file into a string and returns it
spit      Opposite of slurp. Opens file with writer, writes content, then closes it
Interoperability with Java

Clojure can use Java objects, methods, and fields. This includes Java's collections data types, although Clojure's datatypes are preferable because they are immutable and persistent. Possibly the best use of Java in Clojure is to make Swing GUIs.

(import import-lists*)
Imports the named Java classes. Example: (import '(java.awt.Color) '(java.awt.Point)).
Only classes can be imported, not entire packages; there is no equivalent of import java.util.*;
All of java.lang is implicitly imported, along with everything in clojure.core.
(new JavaClassName args*)
(JavaClassName. args*) ; syntactic sugar for the above--notice the period, it's important!
Creates and returns a new Java object of the specified class. Examples: (def rnd (new java.util.Random)), (def c (java.awt.Color. 255 128 0))
(. JavaObject methodName args*)
(.methodName JavaObject args*)
Calls the Java object's method with the supplied arguments. Examples: (. rnd nextInt 10), (.nextInt rnd 10).
(. JavaObject fieldName)
Returns the value in the named field of the Java object. Example: After (def p (new java.awt.Point 20 40)), (. p x) will return 20.
(JavaClass/staticMethodName args*)
Calls the static method of the class. Example: (System/currentTimeMillis).
JavaClass/staticFieldName
Returns the value in the static field of the class. Example: (java.awt.Color/CYAN).
doto(JavaInstanceOrClass methodCalls*)
Uses the Java object or class as the receiver for any number of method calls. Example: (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2)).
(proxy [Class? Interfaces*] [constructorArgs*] functions*)
Creates a Clojure class to do the work that would be done by a Java class in Java. The optional Class? parameter names the superclass (and if omitted, defaults to Object). If the class's constructor requires parameters, those are provided in the second vector, otherwise the second vector is emtpy, []. The functions are usual Clojure functions (omitting the defn), and have the form (name [params*] body) or (name ([params*] body) ([params+] body) ...). For example,
(. convert-button
  (addActionListener
    (proxy [ActionListener] []
      (actionPerformed [evt]
        (let [c (Double/parseDouble (. temp-text (getText)))]
          (. fahrenheit-label
            (setText (str (+ 32 (* 1.8 c)) " Fahrenheit"))))))))
There are also functions for dealing with Java arrays, and for treating Java methods as first-class objects. These are not covered here.
