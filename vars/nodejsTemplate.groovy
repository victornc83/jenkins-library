#!/usr/bin/groovy

def call(project, body){
    podTemplate(
      name: 'nodejs',
      label: 'nodejs',
      cloud: "openshift",
      namespace: project,
      envVars: [],
      containers: [
        containerTemplate(
          name: 'jnlp',
          image: 'openshift/jenkins-slave-nodejs-centos7',
          ttyEnabled: true,
          workingDir: '/tmp',
          args: '${computer.jnlpmac} ${computer.name}',
          envVars: []
        )
      ]
    ) {
        node('nodejs'){
            body()
        }
      }
}
