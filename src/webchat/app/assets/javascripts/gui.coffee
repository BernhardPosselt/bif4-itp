###
This class binds all click events and gui interactions
###

class Gui

    constructor: (@manager) ->
        key_codes = new KeyCodes()
        @submit_key = key_codes.enter
        @cancel_key = key_codes.escape
        @visible_popup_window = undefined
        # left sidebar dom elements
        @dom_channel_tab = $ ".channels_link"
        @dom_group_tab = $ ".groups_link"
        @dom_channels_list =  $ "#channel_sidebar #channels"
        @dom_channel_list.remove()
        @dom_groups_list = $ "#channel_sidebar #groups"
    
        # initalize methods
        @init_left_sidebar_tabs()
        
        
    # intializes the left sidebar navigation via tabs
    init_left_sidebar_tabs: ->
        @dom_channel_tab.click ->
            if not @dom_channels_list.is(":visible")
                @dom_groups_list.fadeOut "fast", ->
                    @dom_channels_list.fadeIn "fast"
            false
        @dom_group_tab.click ->
            if not @dom_groups_list.is(":visible")
                @dom_channels_list.fadeOut "fast", ->
                    @dom_groups_list.fadeIn "fast"
            false
