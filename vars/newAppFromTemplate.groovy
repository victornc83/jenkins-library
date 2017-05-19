#!/usr/bin/groovy

def call(body){

  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def params = ''
  loginOpenshift(config.project){
    sh "oc project"
    for(int i = 0; i<config.parameters.size();i++){
      params = params + '-p ' + config.parameters[i] + '=' + config.values[i] + ' '
    }
    sh "oc new-app --template=${config.template} ${params} -n ${config.project}"
    sh "oc project"
  }
}
