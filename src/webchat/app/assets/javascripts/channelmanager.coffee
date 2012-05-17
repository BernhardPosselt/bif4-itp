###
Manages all channel objects in the channellist
###

class ChannelManager

    constructor: (@callback_init) ->
        @dom_elem = $ "#channels ul"
        @dom_reg = {}
        @data = {}
        @active_channel = undefined


    # sets the initial data array
    init: (@data) ->
        @callback_init()

        
    # writes initalized data into the ui
    init_ui:() ->
        @dom_elem.empty()    
        for id, value of @data
            @create_dom(id, value)


    # decides what to do with the input
    # data: the data array
    # actions: the actions array
    input: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create(id, data[id])
                when "update" then @update(id, data[id])
                when "delete" then @delete(id)       


    # creates a new element in the data tree and udpates the dom
    create: (id, data) ->
        @data[id] = data
        @create_dom(id, data)
        

    # creates an element in the channel list and in the appropriate places
    create_dom: (id, data) ->
        elem = $('<li>')
        elem.html( data.name )
        elem.bind 'click', =>
            @join_channel(id)
        @dom_reg[id] = elem
        @dom_elem.append(elem)
        console.log("Created channel " + data.name)        


    # updates an element in the data tree and udpates the dom
    update: (id, data) ->
        @data[id] = data
        @update_dom(id, data)
        

    # updates an element in the channel list and in the appropriate places
    update_dom: (id, data) ->
        elem = @dom_reg[id]
        console.log("Updated channel " + elem.html() + " to " + data.name)
        elem.html( data.name )


    # creates a new element in the data tree and udpates the dom
    delete: (id, data) ->
        delete @data[id]
        @delete_dom(id, data)

    
    # deletes an item from the dom     
    delete_dom: (id) ->
        elem = @dom_reg[id]
        console.log("Removed channel " + elem.html())
        elem.remove()

    
    # get active stream
    get_active_channel: () ->
        # FIXME: this if should return the actual channel
        if @active_channel == undefined
            return 0
        return @active_channel                 
    
    
    # joins a channel
    join_channel: (@active_channel) ->         
        console.log("joined channel " + @data[@active_channel].name)
        
  
