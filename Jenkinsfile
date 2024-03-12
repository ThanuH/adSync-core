podTemplate(
    label: 'adSync-core-jenkinsx',
    containers: [
        containerTemplate(name: 'maven', image: 'maven:3.6.3-jdk-11', command: 'cat', ttyEnabled: true),
    ],
    yaml: 'KubernetesPod.yaml'
) {
    node('adSync-core-jenkinsx') {
        stage("Build") {
            container('maven') {
                sh 'mvn -N io.takari:maven:wrapper'
                sh 'mvn install dockerfile:build -DskipTests=true '
            }
        }
        stage("Push") {
            container('maven') {
                sh 'mvn dockerfile:push'
            }
        }
    }
}
