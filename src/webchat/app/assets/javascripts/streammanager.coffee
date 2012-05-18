class StreamManager

    constructor: () ->
        @dom_elem = $ "#streams"
        @dom_reg = {}
        @data = {}
        @channel_data = {}

    init: (id, data, channel) ->
        @data[id] = data
        @channel_data[id] = channel
        @
        
    create_dom: (id) ->
    
        
    

