###
Gets the messages from the websocket and forwards it to the corresponding 
controllers
###

window.WebChat or= {}

window.WebChat.Distributor = class 

    constructor: () ->
        @controllers = []

    setCache: (@cache) ->

    registerController: (controller) ->
        @controllers.push(
            angular.element(controller)
        )

    notifyController: (message) ->
        message = @cache.fill(message)
        for controllerElement in @controllers
            scope = controllerElement.scope()
            controller = controllerElement.controller()
            if controller.canHandle(message.type)    
                scope.$apply ->
                    switch message.action
                        when 'update' then scope.update(message.data)
                        when 'create' then scope.create(message.data)
                        when 'delete' then scope.delete(message.data)
