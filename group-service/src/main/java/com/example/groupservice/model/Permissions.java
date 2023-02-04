package com.example.groupservice.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permissions{
    private boolean can_send_messages;
    private boolean can_send_media_messages;
    private boolean can_send_audios;
    private boolean can_send_documents;
    private boolean can_send_photos;
    private boolean can_send_videos;
    private boolean can_send_video_notes;
    private boolean can_send_voice_notes;
    private boolean can_send_polls;
    private boolean can_send_other_messages;
    private boolean can_add_web_page_previews;
    private boolean can_change_info;
    private boolean can_invite_users;
    private boolean can_pin_messages;
    private boolean can_manage_topics;
}