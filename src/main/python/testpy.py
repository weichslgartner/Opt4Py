import os
from py4j.java_gateway import JavaGateway
from sklearn import linear_model, decomposition, datasets 
from sklearn.svm import LinearSVC
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA


def evaluator(*args) -> float:
    print(args)
    digits = datasets.load_digits()
    X_digits = digits.data
    y_digits = digits.target
    n_samples = len(X_digits)
    X_train = X_digits[:int(.9 * n_samples)]
    y_train = y_digits[:int(.9 * n_samples)]
    X_test = X_digits[int(.9 * n_samples):]
    y_test = y_digits[int(.9 * n_samples):]
    pipeline = Pipeline([('scaling', StandardScaler()),
                     ('pca', PCA(n_components=args[0])),
                      ('svc',LinearSVC(C=args[1]))])
    pipeline.fit(X_train,y_train)
    score = pipeline.score(X_test,y_test)
    
    return float(score)

def python_to_java_array(p_array : list):
    j_array = gateway.new_array(gateway.jvm.java.lang.Integer,len(p_array))
    for i, element in enumerate(p_array):
        j_array[i] = element
    return j_array

if __name__ == "__main__":
    #java_path = "C:\\Users\\andreas\\Desktop\\andreas\\opt4jpython\\optexec\\bin\\"
    #os.system(java_path + "java JavaHost")
    gateway = JavaGateway()  
    opt_app = gateway.entry_point
    array1 = python_to_java_array([1,1])
    array2 = python_to_java_array([12,12])
    opt_app.setPythonPath(os.path.dirname(os.path.realpath(__file__)))
    opt_app.config("int", array1, array2);
    opt_app.run()