using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace parking.Models
{
    [Table("tb_occupation")]
    public class Occupation
    {
        [Key]
        public string proprio_id { get; set; }
        public string proprio_nom { get; set; }
        public string mt_type { get; set; }
        public string mt_matricule { get; set; }
        [ForeignKey ("Place")]
        public int id_place { get; set; }
        public string email { get; set; }
        public string randomPassword { get; set; }

        public Place Place { get; set; }
    }
}
