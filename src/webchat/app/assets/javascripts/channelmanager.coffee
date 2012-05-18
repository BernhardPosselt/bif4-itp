###
Manages all channel objects in the channellist
###

class ChannelManager

    constructor: (@callback_init, @main_manager) ->
        @dom_channel_list = $ "#channels ul"
        @dom_stream = $ "#streams"
        @dom_stream_sidebar_users = $ "#info_sidebar #channel_users"
        @dom_stream_sidebar_files = $ "#info_sidebar #channel_files"
        @dom_reg_channel_list = {}
        @dom_reg_stream = {}
        @dom_reg_stream_sidebar_users = {}
        @dom_reg_stream_sidebar_files = {}
        @channel_data = {}
        @stream_data = {}
        @stream_sidebar_users_data = {}
        @stream_sidebar_files_data = {}
        @active_channel = undefined
        @loaded_channels = {}


    # sets the initial data array
    init: (@channel_data) ->
        @callback_init()

        
    # writes initalized data into the ui
    init_ui:() ->
        @dom_channel_list.empty()
        @dom_stream.empty()
        @dom_stream_sidebar_users.empty()
        @dom_stream_sidebar_files.empty()
        for id, value of @channel_data
            @create_dom(id, value)
        @join_first_channel()


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
        @create_dom(id)
        

    # creates an element in the channel list and in the appropriate places
    create_dom: (id) ->
        # create channel list item
        channel_data = @channel_data[id]
        list_entry = $("<li>")
        list_entry.html( channel_data.name )
        list_entry.bind 'click', =>
            @join_channel(id)
        @dom_reg_channel_list[id] = list_entry
        @dom_channel_list.append(list_entry)
        
        # create the stream div
        stream = $("<div>")
        stream.addClass("stream")
        # create fieldset around content
        stream_field = $("<fieldset>")
        stream_field.addClass("stream_field")
        stream_label = $("<legend>")
        stream_label.addClass("stream_name")
        stream_label.html(channel_data.name)
        # create div data in fielset
        stream_meta = $("<div>")
        stream_meta.addClass("stream_meta")
        stream_meta.html(channel_data.topic)
        stream_chat = $("<div>")
        stream_chat.addClass("stream_chat")
        # now build dom to add
        stream_field.append(stream_label)
        stream_field.append(stream_meta)
        stream_field.append(stream_chat)
        stream.append(stream_field)
        @dom_reg_stream[id] = stream
        @dom_stream.append(stream)
        
        # creat stream sidebar
        
        console.log("Created channel " + channel_data.name)        


    # updates an element in the data tree and udpates the dom
    update: (id, data) ->
        @data[id] = data
        @update_dom(id)
        

    # updates an element in the channel list and in the appropriate places
    update_dom: (id) ->
        channel_data = @data[id]
        channel_list = @dom_reg_channel_list[id]
        channel_list.html( channel_data.name )
        # TODO: update stream and sidebar
        console.log("Updated channel " + channel_data.name)
        

    # creates a new element in the data tree and udpates the dom
    delete: (id) ->
        delete @channel_data[id]
        delete @stream_data[id]
        delete @stream_sidebar_files_data[id]
        delete @stream_sidebar_users_data[id]
        delete @loaded_channels[id]
        @delete_dom(id)
        # check if we got another channel and if a channel exists, join the
        # first one 
        @join_first_channel()

    
    # deletes an item from the dom     
    delete_dom: (id) ->
        # remove channel list entry
        channel_entry = @dom_reg_channel_list[id]
        console.log("Removed channel " + channel_entry.html())
        channel_entry.remove()
        delete @dom_reg_channel_list[id]
        # remove stream
        stream = @dom_reg_stream[id]
        stream.remove()
        delete @dom_reg_stream[id]
        # remove users list
        users = @dom_reg_stream_sidebar_files[id]
        delete @dom_reg_stream_sidebar_files[id]
        # remove files list
        files = @dom_reg_stream_sidebar_files[id]
        delete @dom_reg_stream_sidebar_files[id]

    
    # get active stream
    get_active_channel: () ->
        return @active_channel                 
    
    
    # joins the first channel in the list or displays nothing
    join_first_channel: () ->
        console.log("joined first channel")
        if @_get_dict_size(@channel_data) > 0
            for id, value of @channel_data
                @join_channel(id)
                break
        else
            console.log("no channel found, can not join initial channel")
            # TODO: display message that no channel is being found
   
   
   # returns the size of a dictionairy 
    _get_dict_size: (dict) ->
        size = 0
        for key of dict
            if dict.hasOwnProperty(key)
                size++
        return size
              
    
    # joins a channel
    join_channel: (@active_channel) -> 
        # check if we need to get data to init the stream
        if @loaded_channels[@active_channel] == undefined
            msg = 
                type: "join"
                data: 
                    channel: @active_channel
            @main_manager.send_websocket(msg)      
            @loaded_channels[@active_channel] = true
        # remove unread flag    
        list_entry = @dom_reg_channel_list[@active_channel]
        list_entry.removeClass("unread")
        # fade out inactive ones and fade in active one 
        @dom_stream.children(".stream").fadeOut "fast", =>
            @dom_reg_stream[@active_channel].fadeIn "fast"
        @dom_stream_sidebar_users.children(".users").fadeOut "fast", =>
            @dom_reg_stream_sidebar_users[@active_channel].fadeIn "fast"
        @dom_stream_sidebar_files.children(".files").fadeOut "fast", =>
            @dom_reg_stream_sidebar_files[@active_channel].fadeIn "fast"
        console.log("joined channel " + @channel_data[@active_channel].name)
        
    
    # initializes a stream with data
    init_stream: (data) ->
        for id, stream_data of data
            @create_stream(id, stream_data)    
    
    
    # receives input for a stream
    input_stream: (data, actions) ->
        # messages can only be created, so ignore the actions array
        for id, msg_data of data
            @create_stream(id, msg_data)

    
    # creates the values for a message
    create_stream: (id, data) ->
        # if channel is loaded append message
        if @loaded_channels[id]
            for key, value of data
                if not @stream_data[id]
                    @stream_data[id] = {}
                @stream_data[id][key] = value
                @create_stream_dom(id, key, value)
        # if channel is not the active one, add the unread class
        if id != @active_channel
            elem = @dom_reg_channel_list[id]
            console.log elem
            if not elem.hasClass("unread")
                elem.addClass("unread")
             
    
    # creates the dom for a message
    create_stream_dom: (channel_id, msg_id) ->
        data = @stream_data[channel_id][msg_id]
        stream = @dom_reg_stream[channel_id]
        line = $("<p>")
        line.addClass("line")
        user = $("<span>")
        user.addClass("user")
        date = $("<span>")
        date.addClass("date")
        msg = $("<span>")
        msg.addClass("message")
        msg.html(": " + data.message)
        # convert unixtimestamp to date
        date_string = new Date(data.date)
        year = date_string.getFullYear()
        month = date_string.getMonth()
        day = date_string.getDay()
        hours = date_string.getHours()
        minutes = date_string.getMinutes()
        seconds = date_string.getSeconds()
        date_string = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds
        date.html(date_string)
        # get username
        user_data = @main_manager.users.get_user(data.user_id)
        user_name = " " + user_data.prename + " " + user_data.lastname
        user.html(user_name)
        line.append(date)
        line.append(user)
        line.append(msg)
        stream.append(line)
    
    
    # initializes a stream with data
    init_file: (data) ->
        #@streams.init(data)    
    
    
    # receives input for a stream
    input_file: (data, action) ->
  
