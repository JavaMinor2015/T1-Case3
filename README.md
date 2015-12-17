# T1-Case3
Kantilever business logic, REST API & Angular Frontend.

![Compiling](http://imgs.xkcd.com/comics/compiling.png)

## Technologie
__Kantilever__

* Spring backend
* Spring REST + Hateoas
* Angular Frontend

__Android__

* Android 6.0
* REST Volley 

## Structuur
__Project__

* Kantilever
    * __Modules__ 
        * __Domain__ 
            _Entiteiten die in alle modules gebruikt worden_
            
        * __PlatformServices__ 
            _Processen/utilities die in alle modules gebruikt kunnen worden, bijv. abstracte repository_
            
        * __FE_WebWinkel__
            _Frontend klant bestellen, AngularJS_    
            
        * __BS_Catalogus__
            _Catalogus implementatie, bijv. repository implementaties_
            
        * __BS_Voorraadbeheer__
            _Voorraadbeheer implementatie met integratieservice / servicebus_   
            
        * __BS_Klantbeheer__
            _Klantbeheer implementatie, bijv. repository implementaties_   
            
        * __BS_Bestellingbeheer__
            _Bestellingbeheer implementatie, bijv. repository implementaties_
            
        * __PCS_Winkelen__
            _Processlogica voor winkelen, alle servicemethoden die aangeroepen worden door de frontends_
            
        * __PCS_Bestellen__
            _Processlogica (REST) voor bestellen, alle servicemethoden die aangeroepen worden door de android frontend_
            
* Android
    * __Modules__
        * App
        
        
## Git

* master - alleen releases via een release branch
* dev - features/bugs etc. die afgerond zijn
* release/naam - een nieuwe release voorbereiden
* feature/naam - een nieuwe feature voorbereiden

__Voor merge naar dev:__

1. merge dev naar feature: solve conflicts
1. slagen unit tests?
1. slaagt maven build?
1. SonarQube issues?
1. Feature request naar Tom

__Tom__

1. Feature request accept/reject
1. Merge in dev

__Matthijs__

1. Maak release branch aan
1. Slagroom op de taart
1. Merge naar master

        
## Code

__Abstractie, bijv. Repository__
    
* Abstracte Repository (al dan niet interface) met daarin koppeling naar database type
* Repository implementatie (al dan niet interface) met daarin extends naar abstracte repository, bijv:
    * CustomerRepo - findByFirstName(), findByLastName() etc.
    * ProductRepo - findByCategory() etc.

__Documentatie__

* Elke publieke methode documentatie geven voor de feature af is.
* Vage constructies inline documenteren (lambda)

## Links

* [JIRA](http://10.32.43.248:8085/jira/browse/TEAMONE/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel)
* [Sonar](http://10.32.43.248:9000/sonar/)
* [Jenkins](http://10.32.43.249/jenkins/view/Team%201/)
* [Stash](http://10.32.43.248:7990/stash/projects)
* [Link to android git here]