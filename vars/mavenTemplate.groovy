#!/usr/bin/groovy

def call(Map parameters = [:], body){
    def namespace = parameters.get('namespace','myproject')
    podTemplate(label: 'maven',
      namespace: '${namespace}',
      containers: [containerTemplate(name: 'maven', image: 'openshift/jenkins-slave-maven-centos7', args: '${computer.jnlpmac} ${computer.name}')]
      ) {
        body()
      }
}
