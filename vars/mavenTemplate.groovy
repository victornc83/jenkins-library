#!/usr/bin/groovy

def call(project, body){
    podTemplate(
      name: 'maven',
      label: 'maven',
      cloud: "openshift",
      namespace: project,
      envVars: [],
      containers: [
        containerTemplate(
          name: 'jnlp',
          image: 'openshift/jenkins-slave-maven-centos7',
          ttyEnabled: true,
          workingDir: '/tmp',
          args: '${computer.jnlpmac} ${computer.name}',
          envVars: []
        )
      ]
    ) {
        node('maven'){
            body()
        }
      }
}
