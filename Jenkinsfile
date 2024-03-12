pipeline { 
  agent {
    kubernetes {
      label 'adSync-core-jenkinsx'
      yamlFile 'KubernetesPod.yaml'
    }
  }   
   stages {  
	// stage("Unit Tests") {
	//         steps {
	//             container('maven') {
	//                 sh 'mvn test' 
	//             }
	//             } 
	//         post {  
	//             always { 
	//                 junit testResults: '**/target/surefire-reports/TEST-*Test.xml', allowEmptyResults: true
	//             }
	//         }
	//     } 
	//     stage("Integration Tests") {
	//         steps {						     
	//             container('maven') {  
	//                 sh 'mvn verify -DskipUTs'  
	//             }
	//         } 
	//         post { 
	//             always { 
	//                 junit testResults: '**/target/failsafe-reports/TEST-*IT.xml', allowEmptyResults: true   
	//             }
	//         }
	//     }
	//   	stage('SonarQube Analysis') {
 //        		steps {
 //            		withSonarQubeEnv('sonarqube') {
 //            			container('maven') { 
 //                			sh 'mvn sonar:sonar'
 //                			echo 'env.SONAR_HOST'
 //                		}
 //            		    }
 //        		}
 //    		}
	// 	stage('Process Reports') {
	// 	    steps {
	// 	    	container('maven') {
	// 		    script {
	// 		     echo 'Allure Reports: '+env.BUILD_URL+'allure'
	// 			sh 'mkdir target/allure-results'
	// 		     sh 'chmod -R o+xw target/allure-results'
	// 		            allure([
	// 		                    includeProperties: false,
	// 		                    jdk: '',
	// 		                    properties: [],
	// 		                    reportBuildPolicy: 'ALWAYS',
	// 		                    report: 'target/allure-report',
	// 		                    results: [[path: 'target/allure-results']] 
	// 		            ])
		           
	// 		     }
	// 		    }
	// 	    }
	// 	} 
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
	//   stage("argocd") {
	//     steps {
	//         container('docker') {
			
		
	// 	        sh ''' git config --global user.email "argocd@ipay.com" '''
 //                        // sh ''' git config --global user.name "Argocd" '''
			
			
	// 	withCredentials([gitUsernamePassword(credentialsId: 'cloudadmin-git-token', gitToolName: 'Default')]) {
	// 	   sh "git clone https://github.com/LOLC-Technologies/ipay-txn-processor-infra.git"
	// 	   sh '''sed -i '$s/.*/'"#$BUILD_TAG"'/' "ipay-txn-processor-infra/dev/deployment.yaml" '''
	// 	  sh ''' cd ipay-txn-processor-infra && ls -ltr && git status && git add . && git commit -m "jenkins-argocd" && git push https://github.com/LOLC-Technologies/ipay-txn-processor-infra.git main'''
                 
 //                }
	//         }
	//      }
	// }	
  }
}   
 
