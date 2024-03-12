pipeline {
  agent {
    kubernetes {
      label 'adSync-core-jenkinsx'
      yamlFile 'KubernetesPod.yaml'
    }
  }   
    stages {  
	stage("Build") {
	    steps {
	        container('maven') {
        		sh 'mvn -N io.takari:maven:wrapper'
				sh 'mvn install dockerfile:build -DskipTests=true '
	        }
	     }
	}  
	stage("Push") {
	    steps {
	        container('maven') {
			sh 'mvn dockerfile:push'
	        }
	     }
	}

     }
}
 
