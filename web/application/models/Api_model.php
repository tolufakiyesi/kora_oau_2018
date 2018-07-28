<?php
defined('BASEPATH') OR exit ('No direct script access allowed');

class Api_model extends CI_Model{

    var $client_service = "kora-app-client";
    var $auth_key = "kora-road20hub";

    public function __construct()
    {
        parent::__construct();
        $this->load->database();

    }
    public function validate_auth()
    {
        $client_service = $this->input->get_request_header('Client-Service', TRUE);
        $auth_key = $this->input->get_request_header('Auth-Key', TRUE);


        if ($client_service == $this->client_service && $auth_key == $this->auth_key){
            return true;
        }

        return false;

    }

    public function create_plan($account){
        if($this->db->insert('accounts', $account)){
            return $this->get_by_id($this->db->insert_id());
        }
        return false;
    }

    public function modify_plan(){

    }

    public function get_user_plan(){
        $user_id = $this->input->get_request_header('User-Id', TRUE);
        // $this->db->from('accounts');
        $this->db->where('user_id', $user_id);
        return $this->db->get('accounts')->result();
    }

    public function get_by_id($id){
        $this->db->from('accounts');
        $this->db->where('id', $id);
        return $this->db->get()->row();
    }

}