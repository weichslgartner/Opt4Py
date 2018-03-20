# Opt4Py
First try to couple the optimization framework Opt4J with Python.
For now a simple hyperparameter search for sci-kit learn.

# Build
build java part with gradle

# Run
Start first the JavaHost server and then run the Python script.
The Python Script will start Opt4J and the Opt4J evaluator will call the Python evaluator function.

# Unsatisfied Link Error
you need the native library for JEP.
See https://github.com/ninia/jep/wiki/FAQ
