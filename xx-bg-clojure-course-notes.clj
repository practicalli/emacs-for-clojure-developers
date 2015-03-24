Workshop problem
Starting the day with this problem:

Given a text file, let’s find the n most common and uncommon
words. Ignore certain stop words.

Lesson taught: Data > Code.

My solution:

(def text-file
  "http://www.gutenberg.org/cache/epub/19033/pg19033.txt"
  (str (System/getenv "HOME") "/Downloads/pg19033.txt"))
 
(def stop-words
  #{"you"
    "the"
    "and"
    "a"
    "to"
    "of"
    "was"
    "i"})
 
(defn get-words
  [stop-words text-file]
  (remove stop-words
          (map string/lower-case
               (re-seq #"\w+" (slurp text-file)))))
 
(defn count-words
  [words]
  (sort (map #(vector (val %) (key %))
             (frequencies
              words))))
 
(defn main
  []
  (let [freqs (count-words
               (get-words stop-words text-file))]
    (println (take 5 freqs))
    (println (take-last 5 freqs))))</pre>
Output:
 
([1 0] [1 000] [1 12] [1 1500] [1 1887])
([144 said] [177 alice] [203 it] [228 in] [241 she])
How to write your own frequencies function and introducing how to use
reduce and update-in:

(defn freqs
  [words]
  (reduce (fn [res w]
            (update-in res [w]
                       (fnil inc 0)))
          {}
          words))<
Why fnil is better than an if form – telling computer what vs. how.

A better way to sort was shown by BG:

(defn top-words
  [word-counts]
  (sort-by (comp - val) word-counts))
 
(top-words (frequencies ["a" "a" "b" "c"]))
#= (["a" 2] ["b" 1] ["c" 1])
Introduction to threading macros to simplify series of function
compositions.

Amazing how much has been covered and BG hasn’t mention the let form
at all so far (although I’m using it).

After seeing BG’s code, I realized why I couldn’t get juxt working in
my own main function – I was trying to call map on juxt and
messing things up, instead I should have just called the result of
juxt:

(defn main*
  []
  ((juxt #(take 5 %) #(take-last 5 %))
   (count-words
    (get-words stop-words text-file))))
 
#= [([1 "0"] [1 "000"] [1 "12"] [1 "1500"] [1 "1887"])
#  ([144 "said"] [177 "alice"] [203 "it"] [228 "in"] [241 "she"])]
The star at the end of a name is a convention which means it is an
alternative version or slightly different version.

3 Multimethods
How to have “objects”? Use multi-methods.

Dispatch on an arbitrary function
Ad-hoc hierarchies
Java uses static dispatch because method invocation, i.e. method of
which type to call, is decided by the compiler at compile-time (at least
for simple cases).

Java is also single-dispatch because it can dispatch on only one
factor – the type (i.e. the class and the arity of the method).

Hierarchies and type concepts are tied together in Java.

Example of how to do multimethods in Clojure:


(def unix
  {:os ::unix
   :c-compiler "cc"
   :home "/home"})
 
(def osx
  {:os ::osx
   :c-compiler "gcc"
   :home "/Users"})
 
(defmulti home :os)
 
(defmethod home ::unix
  [m]
  (:home m))
 
(home unix)
;= "/home"
 
(home osx)
;= IllegalArgumentException No method in multimethod
;  'home' for dispatch value: :example1.core/osx
The ::keyword is used to confine the keyword to the current namespace.

The argument is a dispatch function which should not have side-effects.

Since OS X is a derivative of Unix, use derive:

1
2
3
4
(derive ::osx ::unix)
 
(home osx)
;= "/Users"
So this means that osx “derives the functionality” from unix because
we are creating a hierarchy.

This is going to take some time to digest… the dispatch here is on a
map which is the idiom in Clojure compared to dispatching on the class
in Java.

Introducing multiple dispatch by dispatching on both :os and
compiler.

Use :default to give a default multimethod implementation.

Introducing ancestors and descendants to introspect the hierarchy.

Introducing prefers to handle when multiple ancestors match for a
multimethod call.

Recommendation to read http://clojure.org/multimethods

Discussion on what is the difference between a class and a type? In
Java, there is no difference. But they are actually orthogonal concepts.

So use a type to differentiate / switch behavior and we can use a simple
map data structure or a keyword or any simple values in Clojure as a
“type”, instead of depending entirely on “classes” in traditional OOP
languages. A class is a bag of data and behavior.

Taking duck-typing as an example, as long as a parameter matches some
“behavior” (type), we can use that parameter regardless of what “class”
it belongs to.

On the other side, a class can have many “types”, for example, a vector
can also behave as a collection and can behave as a sequence.

Recommendation to read
http://thinkrelevance.com/blog/2009/08/12/rifle-oriented-programming-with-clojure-2

Point is to keep data and functionality separate and not complect it
into classes.

Multimethods are extensible, they are global and hence users of the code
can extend the multimethod to more types.

Example of built-in print-method.

4 Protocols & Types
Introduction to protocols & types: How do you extend a third-party
library without access to it’s source code?

In OOP languages, you can create a new class and subclass existing
third-party class and interoperate bidirectionally.

In FP languages, you can create a new method and make it work on
existing third-party classes.

You have to choose either one in traditional OOP and FP languages.

This is called the Expression Problem.

In current languages, the solution is usually monkey-patching (think of
find_by* methods in Rails ActiveRecord) which can be full of surprises
and brittleness.

Discussion on how you can use visitor pattern in Java to add new
functionality on top of existing classes, but you lose identity – you
may expect Student but you get MyStudent.

In Clojure, you can do both, in a clean manner.

We can use multimethods but the limitation is that it is global.

Multimethods have the advantage of multiple dispatch. Protocols are
single-dispatch on type.

The separation between types and behaviors in Clojure enables the
concept of protocols.

Protocols can be confined to one namespace. Multimethods are global.

Example uses records – records behave like a map but has a type
(identity) attached to it.


(defprotocol IPalindrome
  (palindrome? [o]
    "Check whether o is a palindrome."))
 
(defrecord student
    [name email])
 
(extend-protocol IPalindrome
  java.lang.String
  (palindrome? [s]
    (= s (apply str (reverse s))))
 
  student
  (palindrome? [s]
    (palindrome? (:name s))))
 
(palindrome? "malayalam")
;= true
 
(palindrome? (-&gt;student "malayalam" "b@b.com"))
;= true
extend-protocol calls extend underneath which is just associating a
type with a protocol with a map data structure of function names to
implementations.

You can check if a type extends? a protocol or an object satisfies?
a protocol.

Introducing reify – reification means given an abstraction, create a
concretion. It allows you to create anonymous implementations of any
protocol.

(def *anon
  (reify IPalindrome
    (palindrome? [_] true)))
 
(palindrome? *anon)
;= true
You can use lexical closures inside a function and returns an object of
anonymous type (a dynamic implementation) that satisfies a protocol.

Limitation of reify is it cannot instantiate classes, it can only
instantiate protocols and interfaces. For classes, you can use proxy
which is mainly used for Java interoperability.

Advantage of protocol is that you can group functions and check if a
type extends that protocol. That is the difference from multimethods.

Example of how to use protocols to create mixins with example of
IOFactory implementations in the Clojure source code – data all the
things!

Internally, Clojure uses interfaces and ClojureScript uses protocols. In
future, Clojure will internally switch to protocols as well.

Example of ChainMap data structure in Python and BG’s ChainMap implementation in Clojure by using protocols.

5 Concurrency
Handle state properly and you’ll get concurrency for free. Get the
basics right.

BG gave example of his experience in Cleartrip.com about state and
mutability.

In Clojure, identity and value are separated.

value
immutable data in a persistent structure
identity
series of causally related values over time
state
identity at a point in time
Example: “bank balance” is an identity, it’s value changes over time and
it’s current value is it’s current state. You can put the “bank balance”
inside a container which can decide the semantics of how you can change
it’s state.

The “containers” are refs, atoms, agents and vars.

shared	isolated
synchronous /	refs / stm	-
coordinated		
synchronous /	atoms	vars
autonomous		
asynchronous /	agents	-
autonomous		
In Clojure, there is optimistic concurrency.

Unified update model:

update by function application
readers require no coordination
readers never block anybody
writers never block readers
In single-threaded-view languages, you will use locks which prevents
reading from others as well.

ref	atom	agent	var
create	ref	atom	agent	def
deref	deref/@	deref/@	deref/@	deref/@
update	alter	swap!	send	alter-var-root

;; Use deref function or @ reader macro to dereference a reference type
;; and get the value behind it.

;; Vars are special because they are deref-ed automatically.

first
;= #&lt;core$first clojure.core$first@2be06d39&gt;
 
(var first)
;= #'clojure.core/first
 
#'first
;= #'clojure.core/first
 
@#'first
;= #&lt;core$first clojure.core$first@2be06d39&gt;

;; STM-related functionality like alter and commute have to be in a
;; dosync form (transaction).

;; vars can be rebound:

;; api	scope
;; alter-var-root	root binding
;; set!	thread-local, permanent
;; binding	thread-local, dynamic
;; Dynamic scope is imperative, not functional. Looking at the code, you
;; cannot know what value it will have.

;; 6 Parallelism
;; Parallelism is not same as concurrency.

;; In concurrency, there is at least one resource being shared.

;; All along, we have multiple processes running on a single CPU core
;; because we have concurrent processes, i.e. there is
;; scheduling. Concurrent code can be sequential or parallel.

;; Parallelism is about running multiple processes at the same time across
;; multiple CPU cores. Parallelism is highest when there are no shared
;; resources.

;; If code is written properly w.r.t. concurrency, you will get parallelism
;; for free.

;; Proper code written can run as fast as possible on a single-core machine
;; or a multiple-core machine. But non-concurrent code (use locks, etc.)
;; can run as fast as possible on a single-core machine but will not
;; increase in performance on a multiple-core machine.

;; Example is Postgresql vs. MySQL performance comparison on scaling with more CPU cores.

;; See future, pmap, pcalls, pvalues and
;; java.lang.concurrent.Executor.

;; 7 Macros
;; DSLs via Macros

;; Macros are functions that run at compile-time that will generate data
;; which will be treated as code at run-time.

;; Syntax quote (back-tick)
;; Unquote (tilde)
;; Unquote splicing (tilde and at-symbol)
;; Use the contents of the list directly
;; Variable capture
;; Gensym
;; Possible because code and data are same.

;; If you want to create an unless form which is semantically a boolean
;; opposite of the when form (an if form with only the then form,
;; i.e. no else form), you cannot use a function because the parameters
;; will be evaluated before calling the function, so you will need a macro
;; here.

(defmacro unless
  [test &amp; body]
  `(when (not ~test)
     ~@body))
 
(def dead? false)
 
(unless dead? (println "Alive"))
;= "Alive"
 
(def dead? true)
 
(unless dead? (println "Alive"))
;= nil

;; Using a hash at the end of a new var name inside a macro means a
;; universally unique name is generated so that there are no name clashes
;; with other code that the macro expansion is part of. It is same as
;; calling gensym function manually.

;; There is a Clojure built-in that does the same as unless but it is
;; given a better name – when-not.

;; Example of a recursive macro:

(defmacro do-until
  "cond's lost cousin"
  [&amp; clauses]
  (when clauses
    `(when ~(first clauses)
       ~(if (next clauses)
          (second clauses)
          (throw (IllegalArgumentException.
                  "do-until needs an even number of forms.")))
       (do-until ~@(nnext clauses)))))
 
(do-until true (prn 1) false (prn 2))
;= 1
Introducing a longer example of using macros and functions to create a
quite complex DSL. And finally generated XML out of it! Wow.

