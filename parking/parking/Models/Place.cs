using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace parking.Models
{
    [Table("tb_place")]

    public class Place
    {
        [Key]
        public int num_place { get; set; }
        public string type_place { get; set; }
        public string disponible { get; set; }
    }
}
