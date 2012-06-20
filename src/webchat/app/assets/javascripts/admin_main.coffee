$(document).ready ->

    load_admin_divs = (hash) ->
        switch hash
            when "#users" then fade_out_visible_divs $("#admin_site_users")
            when "#groups" then fade_out_visible_divs $("#admin_site_groups")
            when "#channels" then fade_out_visible_divs $("#admin_site_channels")
            when "#files" then fade_out_visible_divs $("#admin_site_files")
            
    fade_out_visible_divs = (fadeInElement) ->
        $("#admin_site > div:visible").fadeOut "fast", ->
            fadeInElement.fadeIn()
    
    filter_table_rows = (search_string) ->   
        $("#admin_site > div:visible .data_table tbody tr").each ->
            visible = false
            $(@).children("td").each ->
                if $(@).html().toLowerCase().indexOf(search_string.toLowerCase()) != -1
                    visible = true
            if visible
                $(@).show()
            else
                $(@).hide()    

    # if hash is set, load the divs
    if window.location.hash
        load_admin_divs window.location.hash
    
    # bind tab links
    $("#admin_tabs #admin_tabs_users").click ->
        load_admin_divs("#users")
    $("#admin_tabs #admin_tabs_groups").click ->
        load_admin_divs("#groups")
    $("#admin_tabs #admin_tabs_channels").click ->
        load_admin_divs("#channels")
    $("#admin_tabs #admin_tabs_files").click ->
        load_admin_divs("#files")

    # bind search form
    $(".searchbox").keyup ->
        filter_table_rows $(@).val()
        
    $("#admin_site .data_table").tablesorter(); 
    
    # bind dropdown input filters
    # users
    $("#admin_site_users .actions .filter_groups").change ->
        data = 
            groups: $("#admin_site_users .actions .filter_groups").val()
            channels: $("#admin_site_users .actions .filter_channels").val()
        $("#admin_site_users .data_table tbody"). load "/admin/ajax/users/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
    $("#admin_site_users .actions .filter_channels").change ->
        data = 
            groups: $("#admin_site_users .actions .filter_groups").val()
            channels: $("#admin_site_users .actions .filter_channels").val()
        $("#admin_site_users .data_table tbody"). load "/admin/ajax/users/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
    
    # groups
    $("#admin_site_groups .actions .filter_channels").change ->
        data = 
            users: $("#admin_site_groups .actions .filter_users").val()
            channels: $("#admin_site_groups .actions .filter_channels").val()
        $("#admin_site_groups .data_table tbody"). load "/admin/ajax/groups/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
    $("#admin_site_groups .actions .filter_users").change ->
        data = 
            users: $("#admin_site_groups .actions .filter_users").val()
            channels: $("#admin_site_users .actions .filter_channels").val()
        $("#admin_site_groups .data_table tbody"). load "/admin/ajax/groups/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
    
    # channels
    $("#admin_site_channels .actions .filter_groups").change ->
        data = 	
            groups: $("#admin_site_channels .actions .filter_groups").val()
        $("#admin_site_channels .data_table tbody"). load "/admin/ajax/channels/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
     
    # files    
    $("#admin_site_files .actions .filter_channels").change ->
        data = 
            channels: $("#admin_site_files .actions .filter_channels").val()
        $("#admin_site_files .data_table tbody"). load "/admin/ajax/files/", data, ->
            $("#admin_site .data_table:visible").trigger "update"
